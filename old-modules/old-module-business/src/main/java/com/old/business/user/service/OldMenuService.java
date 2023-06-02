package com.old.business.user.service;

import com.old.business.user.domain.OldMenu;
import com.old.business.user.enums.user.OldMenuEnums;
import com.old.common.mybatis.base.BaseService;

import java.util.List;

/**
 * 菜单权限表  old_menu
 *
 * @author old
 * @date 2023-04-02
 */
public interface OldMenuService extends BaseService<OldMenu> {

    List<OldMenu> selectMenuListByUserId(Integer userId, String menuName, OldMenuEnums.MenuVisibleEnum menuVisible,
                                         OldMenuEnums.MenuStatusEnum menuStatus, Integer adminUserId);

    void updateById(OldMenu oldMenu, Integer userId, String userName);

    void save(OldMenu oldMenu, Integer userId, String userName);

    void removeById(Long id, Integer userId, String userName);

    void removeByIds(List<Long> ids, Integer userId, String userName);
}
