package com.old.business.user.service.impl;


import com.old.business.user.domain.OldRoleDept;
import com.old.business.user.mapper.OldRoleDeptMapper;
import com.old.business.user.service.OldRoleDeptService;
import com.old.common.enums.ResultEnum;
import com.old.common.mybatis.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色和部门关联表  old_role_dept
 * <p>
 * mybatis-plus 提供了 baseMapper 所以这里不再注入
 *
 * @author old
 * @date 2023-04-25
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class OldRoleDeptServiceImpl extends BaseServiceImpl<OldRoleDeptMapper, OldRoleDept> implements OldRoleDeptService {


    @Override
    public void save(OldRoleDept oldRoleDept, Integer userId, String userName) {
        oldRoleDept.setCreateTime(LocalDateTime.now());
        oldRoleDept.setCreateUser(userName);
        oldRoleDept.setCreateUserId(userId);
        this.apiAssert.isFalse(this.save(oldRoleDept), ResultEnum.SAVE_FAIL);
    }

    @Override
    public void deleteRoleDeptByRoleId(Integer roleId) {
        var wrapper = lqw().eq(OldRoleDept::getRoleId, roleId);
        long count = this.count(wrapper);
        log.debug("根据用户id删除角色部门数据：{}, {}", roleId, count);
        if (count <= 0) {
            return;
        }
        int delete = this.baseMapper.delete(wrapper);
        apiAssert.isTrue(delete < count, ResultEnum.DELETE_FAIL);
    }

    @Override
    public void insertRoleDept(Integer roleId, List<Integer> deptIds, Integer createUserId, String createUserName) {
        if (deptIds.isEmpty()) {
            return;
        }
        // 新增角色与部门（数据权限）管理
        LocalDateTime now = LocalDateTime.now();
        List<OldRoleDept> list = new ArrayList<OldRoleDept>(deptIds.size());
        for (Integer deptId : deptIds) {
            OldRoleDept rd = new OldRoleDept();
            rd.setRoleId(roleId);
            rd.setDeptId(deptId);
            rd.setCreateTime(now);
            rd.setCreateUserId(createUserId);
            rd.setCreateUser(createUserName);
            list.add(rd);
        }
        this.apiAssert.isFalse(this.saveBatch(list), ResultEnum.SAVE_FAIL);
    }

}
