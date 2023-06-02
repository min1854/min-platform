package com.old.business.user.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldRole;
import com.old.business.user.domain.OldUser;
import com.old.business.user.domain.req.RoleDataScopeReq;
import com.old.business.user.domain.req.SearchRoleReq;
import com.old.business.user.domain.req.UpdateAndAllocationReq;
import com.old.business.user.domain.resp.AuthMyRoleResp;
import com.old.business.user.mapper.OldRoleMapper;
import com.old.business.user.mapper.OldUserMapper;
import com.old.business.user.service.OldRoleDeptService;
import com.old.business.user.service.OldRoleMenuRelService;
import com.old.business.user.service.OldRoleService;
import com.old.common.base.BaseResultEnum;
import com.old.common.domain.login.RoleBo;
import com.old.common.enums.ResultEnum;
import com.old.common.mybatis.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

/**
 * 角色表  old_role
 * <p>
 * mybatis-plus 提供了 baseMapper 所以这里不再注入
 *
 * @author old
 * @date 2023-04-01
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class OldRoleServiceImpl extends BaseServiceImpl<OldRoleMapper, OldRole> implements OldRoleService {


    @Autowired
    OldRoleDeptService oldRoleDeptService;

    @Autowired
    OldRoleMenuRelService oldRoleMenuRelService;

    @Autowired
    OldUserMapper oldUserMapper;

    @Override
    public List<AuthMyRoleResp> selectRolesByUserId(Integer userId) {
        var userRoles = baseMapper.selectRolePermissionByUserId(userId);
        var roles = this.list(this.lqw().eq(OldRole::getDeleteFlag, false))
                .stream().map(oldRole -> {
                    AuthMyRoleResp resp = new AuthMyRoleResp();
                    BeanUtils.copyProperties(oldRole, resp);
                    return resp;
                }).toList();
        for (var role : roles) {
            for (var userRole : userRoles) {
                if (role.getId().equals(userRole.getId())) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    @Override
    public Page<OldUser> selectUnallocatedList(Integer roleId, String userName, String phone, Integer pageNum, Integer pageSize) {
        return this.oldUserMapper.selectUnallocatedList(roleId, userName, phone, new Page<>(pageNum, pageSize));
    }

    @Override
    public Page<OldUser> selectAllocatedList(Integer roleId, String userName, String phone, Integer pageNum, Integer pageSize) {
        return this.baseMapper.selectAllocatedList(roleId, userName, phone, new Page<>(pageNum, pageSize));
    }

    @Override
    public List<Integer> selectMenuListByRoleId(Integer roleId) {
        OldRole byId = this.getById(roleId);
        apiAssert.isTrue(byId.getDeleteFlag(), ResultEnum.ROLE_NOT_EXISTS);
        return this.baseMapper.selectMenuListByRoleId(roleId, byId.getMenuCheckStrictly());
    }

    @Override
    public List<Integer> selectDeptListByRoleId(Integer id, boolean deptCheckStrictly) {
        return this.baseMapper.selectDeptListByRoleId(id, deptCheckStrictly);
    }

    @Override
    public Page<OldRole> searchRolePage(SearchRoleReq req, Integer pageNum, Integer pageSize) {
        return this.baseMapper.searchRolePage(req, new Page<>(pageNum, pageSize));
    }

    @Override
    public void updateById(OldRole oldRole, Integer userId, String userName) {
        oldRole.setUpdateTime(LocalDateTime.now());
        oldRole.setUpdateUser(userName);
        oldRole.setUpdateUserId(userId);
        this.apiAssert.isFalse(this.updateById(oldRole), ResultEnum.UPDATE_FAIL);
    }

    @Override
    public void save(OldRole oldRole, Integer userId, String userName) {
        oldRole.setCreateTime(LocalDateTime.now());
        oldRole.setCreateUser(userName);
        oldRole.setCreateUserId(userId);
        this.apiAssert.isFalse(this.save(oldRole), ResultEnum.SAVE_FAIL);
    }

    @Override
    public void removeById(Integer id, Integer userId, String userName) {
        OldRole oldRole = new OldRole();
        oldRole.setId(id);
        oldRole.setUpdateTime(LocalDateTime.now());
        oldRole.setUpdateUser(userName);
        oldRole.setUpdateUserId(userId);
        oldRole.setDeleteFlag(Boolean.TRUE);
        this.apiAssert.isFalse(this.updateById(oldRole), ResultEnum.DELETE_FAIL);
    }


    @Override
    public void removeByIds(List<Integer> ids, Integer userId, String userName) {
        LocalDateTime now = LocalDateTime.now();
        List<OldRole> list = ids.stream().map(id -> {
            OldRole oldRole = new OldRole();
            oldRole.setId(id);
            oldRole.setUpdateTime(now);
            oldRole.setUpdateUser(userName);
            oldRole.setUpdateUserId(userId);
            oldRole.setDeleteFlag(Boolean.TRUE);
            return oldRole;
        }).toList();
        this.apiAssert.isFalse(this.updateBatchById(list), ResultEnum.DELETE_FAIL);
    }

    @Override
    public void updateDataScope(RoleDataScopeReq req, Integer updateUserId, String updateUserName) {
        // 修改角色信息 可能还要改变拥有这个角色的登录用户
        this.updateById(req.toRole(), updateUserId, updateUserName);
        // 删除角色与部门关联
        oldRoleDeptService.deleteRoleDeptByRoleId(req.id());
        // 新增角色和部门信息（数据权限）
        oldRoleDeptService.insertRoleDept(req.id(), req.deptIds(), updateUserId, updateUserName);
    }

    @Override
    public void checkRoleAllowed(Integer id, Integer adminId, Integer loginUserId, int adminUserId) {
        apiAssert.isTrue(RoleBo.ADMIN_ID.equals(id) && !loginUserId.equals(adminUserId),
                ResultEnum.NOT_POWER_OPERATE_ADMIN_ROLE);
    }

    @Override
    public void checkRoleDataScope(Integer userId, Integer adminUserId, List<Integer> roleIds, Integer id) {
        apiAssert.isTrue(!adminUserId.equals(userId) && !roleIds.contains(id),
                ResultEnum.ILLEGAL_ACTION);
    }

    @Override
    public void checkRoleNameUnique(Integer id, String roleName) {
        checkUnique(id, () -> this.lambdaQuery()
                .eq(OldRole::getRoleName, roleName)
                .eq(OldRole::getDeleteFlag, false)
                .list(), ResultEnum.ROLE_NAME_EXISTS.format(roleName));
    }

    @Override
    public void checkRoleKeyUnique(Integer id, String roleKey) {
        checkUnique(id, () -> this.lambdaQuery()
                .eq(OldRole::getRoleKey, roleKey)
                .eq(OldRole::getDeleteFlag, false)
                .list(), ResultEnum.ROLE_KEY_EXISTS.format(roleKey));
    }

    @Override
    public void updateAndAllocation(UpdateAndAllocationReq req, Integer updateUserId, String updateUserName) {
        this.updateById(req.toRole(), updateUserId, updateUserName);

        oldRoleMenuRelService.removeByRoleIdAndInsert(req.getId(), req.getMenuIds(), updateUserId, updateUserName);
    }

    private void checkUnique(Integer id, Supplier<List<OldRole>> existsRole, BaseResultEnum errorResult) {
        OldRole byId = this.getById(id);


        apiAssert
                .isNull(byId, ResultEnum.ROLE_NOT_EXISTS)
                .isTrue(byId.getDeleteFlag(), ResultEnum.ILLEGAL_ACTION);

        var exists = existsRole.get();
        if (exists == null || exists.isEmpty()) {
            return;
        }
        apiAssert.isTrue(exists.size() > 1, errorResult)
                .isFalse(exists.iterator().next().getId().equals(id), errorResult);
    }

}
