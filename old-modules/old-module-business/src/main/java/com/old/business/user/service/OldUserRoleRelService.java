package com.old.business.user.service;

import com.old.business.user.domain.OldUserRoleRel;
import com.old.common.mybatis.base.BaseService;

import java.util.List;

/**
 * 用户角色关联表  old_user_role_rel
 *
 * @author old
 * @date 2023-05-14
 */
public interface OldUserRoleRelService extends BaseService<OldUserRoleRel> {

    void insertAuthUsers(Integer roleId, List<Integer> userIds, Integer createUserId, String createUserName);

    void saveUserRoleRel(Integer userId, List<Integer> roleIds, Integer updateUserId, String updateUser);

    void deleteAfterInsert(Integer userId, List<Integer> roleIds, Integer updateUserId, String updateUser);

    void save(OldUserRoleRel oldUserRoleRel, Integer userId, String userName);
}
