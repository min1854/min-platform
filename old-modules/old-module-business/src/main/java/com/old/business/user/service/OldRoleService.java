package com.old.business.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldRole;
import com.old.business.user.domain.OldUser;
import com.old.business.user.domain.req.RoleDataScopeReq;
import com.old.business.user.domain.req.SearchRoleReq;
import com.old.business.user.domain.req.UpdateAndAllocationReq;
import com.old.business.user.domain.resp.AuthMyRoleResp;
import com.old.common.mybatis.base.BaseService;

import java.util.List;

/**
 * 角色表  old_role
 *
 * @author old
 * @date 2023-04-01
 */
public interface OldRoleService extends BaseService<OldRole> {

    List<AuthMyRoleResp> selectRolesByUserId(Integer userId);

    Page<OldUser> selectUnallocatedList(Integer roleId, String userName, String phone, Integer pageNum, Integer pageSize);

    Page<OldUser> selectAllocatedList(Integer roleId, String userName, String phone, Integer pageNum, Integer pageSize);

    List<Integer> selectMenuListByRoleId(Integer roleId);

    List<Integer> selectDeptListByRoleId(Integer id, boolean deptCheckStrictly);

    Page<OldRole> searchRolePage(SearchRoleReq req, Integer pageNum, Integer pageSize);

    void updateById(OldRole oldRole, Integer userId, String userName);

    void save(OldRole oldRole, Integer userId, String userName);

    void removeById(Integer id, Integer userId, String userName);

    void removeByIds(List<Integer> ids, Integer userId, String userName);

    void updateDataScope(RoleDataScopeReq req, Integer updateUserId, String updateUserName);

    void checkRoleAllowed(Integer id, Integer adminId, Integer loginUserId, int adminUserId);

    void checkRoleDataScope(Integer userId, Integer adminUserId, List<Integer> roleIds, Integer id);

    void checkRoleNameUnique(Integer id, String roleName);

    void checkRoleKeyUnique(Integer id, String roleKey);

    void updateAndAllocation(UpdateAndAllocationReq req, Integer updateUserId, String updateUserName);
}
