package com.old.business.user.service.impl;


import cn.hutool.core.util.StrUtil;
import com.old.business.user.domain.OldMenu;
import com.old.business.user.enums.user.OldMenuEnums;
import com.old.business.user.mapper.OldMenuMapper;
import com.old.business.user.service.OldMenuService;
import com.old.common.enums.ResultEnum;
import com.old.common.mybatis.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单权限表  old_menu
 * <p>
 * mybatis-plus 提供了 baseMapper 所以这里不再注入
 *
 * @author old
 * @date 2023-04-02
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class OldMenuServiceImpl extends BaseServiceImpl<OldMenuMapper, OldMenu> implements OldMenuService {

    @Override
    public List<OldMenu> selectMenuListByUserId(Integer userId, String menuName, OldMenuEnums.MenuVisibleEnum menuVisible,
                                                OldMenuEnums.MenuStatusEnum menuStatus, Integer adminUserId) {
        List<OldMenu> menuList = null;
        // 管理员显示所有菜单信息
        if (adminUserId.equals(userId)) {
            menuList = baseMapper.selectList(lqw()
                    .like(StrUtil.isNotBlank(menuName), OldMenu::getMenuName, menuName)
                    .eq(menuVisible != null, OldMenu::getMenuVisible, menuVisible)
                    .eq(menuStatus != null, OldMenu::getMenuStatus, menuStatus)
                    .eq(OldMenu::getDeleteFlag, false)
                    .orderByAsc(OldMenu::getParentId)
                    .orderByAsc(OldMenu::getOrderNum));
        } else {
            menuList = baseMapper.selectMenuListByUserId(userId, menuName, menuVisible, menuStatus);
        }
        return menuList;
    }

    @Override
    public void updateById(OldMenu oldMenu, Integer userId, String userName) {
        oldMenu.setUpdateTime(LocalDateTime.now());
        oldMenu.setUpdateUser(userName);
        oldMenu.setUpdateUserId(userId);
        apiAssert().isFalse(this.updateById(oldMenu), ResultEnum.UPDATE_FAIL);
    }

    @Override
    public void save(OldMenu oldMenu, Integer userId, String userName) {
        oldMenu.setCreateTime(LocalDateTime.now());
        oldMenu.setCreateUser(userName);
        oldMenu.setCreateUserId(userId);
        apiAssert().isFalse(this.save(oldMenu), ResultEnum.SAVE_FAIL);
    }

    @Override
    public void removeById(Long id, Integer userId, String userName) {
        OldMenu oldMenu = new OldMenu();
        oldMenu.setId(id);
        oldMenu.setUpdateTime(LocalDateTime.now());
        oldMenu.setUpdateUser(userName);
        oldMenu.setUpdateUserId(userId);
        oldMenu.setDeleteFlag(Boolean.TRUE);
        apiAssert().isFalse(this.updateById(oldMenu), ResultEnum.DELETE_FAIL);
    }


    @Override
    public void removeByIds(List<Long> ids, Integer userId, String userName) {
        LocalDateTime now = LocalDateTime.now();
        List<OldMenu> list = ids.stream().map(id -> {
            OldMenu oldMenu = new OldMenu();
            oldMenu.setId(id);
            oldMenu.setUpdateTime(now);
            oldMenu.setUpdateUser(userName);
            oldMenu.setUpdateUserId(userId);
            oldMenu.setDeleteFlag(Boolean.TRUE);
            return oldMenu;
        }).toList();
        apiAssert().isFalse(this.updateBatchById(list), ResultEnum.DELETE_FAIL);
    }


}
