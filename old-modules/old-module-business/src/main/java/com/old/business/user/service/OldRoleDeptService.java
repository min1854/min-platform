package com.old.business.user.service;

import com.old.business.user.domain.OldRoleDept;
import com.old.common.mybatis.base.BaseService;

import java.util.List;


/**
 * 角色和部门关联表  old_role_dept
 *
 * @author old
 * @date 2023-04-25
 */
public interface OldRoleDeptService extends BaseService<OldRoleDept> {

    void save(OldRoleDept oldRoleDept, Integer userId, String userName);

    void deleteRoleDeptByRoleId(Integer roleId);

    void insertRoleDept(Integer roleId, List<Integer> deptIds, Integer createUserId, String createUserName);
}
