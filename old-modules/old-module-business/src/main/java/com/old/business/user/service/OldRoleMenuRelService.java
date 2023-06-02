package com.old.business.user.service;

import com.old.business.user.domain.OldRoleMenuRel;
import com.old.common.mybatis.base.BaseService;

import java.util.List;

/**
 * 角色菜单关联表  old_role_menu_rel
 *
 * @author old
 * @date 2023-04-19
 */
public interface OldRoleMenuRelService extends BaseService<OldRoleMenuRel> {

    void save(OldRoleMenuRel oldRoleMenuRel, Integer userId, String userName);

    void removeByRoleId(Integer roleId);

    void removeByRoleIdAndInsert(Integer id, List<Integer> menuIds, Integer operateUserId, String operateUserName);
}
