package com.old.business.user.service;

import com.old.business.user.domain.OldMenuPath;
import com.old.common.mybatis.base.BaseService;

/**
 * 菜单层级关系  old_menu_path
 *
 * @author old
 * @date 2022-10-17
 */
public interface OldMenuPathService extends BaseService<OldMenuPath> {

    void updateByKey(OldMenuPath oldMenuPath, String loginUserName, Integer loginUserId);

    void insert(OldMenuPath oldMenuPath, String loginUserName, Integer loginUserId);

    void removeById(Integer id, String loginUserName, Integer loginUserId);
}
