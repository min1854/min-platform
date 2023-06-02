package com.old.business.user.service.impl;


import com.old.business.user.domain.OldMenuPath;
import com.old.business.user.mapper.OldMenuPathMapper;
import com.old.business.user.service.OldMenuPathService;
import com.old.common.apiAssert.ResultAssertHolder;
import com.old.common.enums.ResultEnum;
import com.old.common.mybatis.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 菜单层级关系  old_menu_path
 *
 * @author old
 * @date 2022-10-17
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class OldMenuPathServiceImpl extends BaseServiceImpl<OldMenuPathMapper, OldMenuPath> implements OldMenuPathService {

    @Autowired
    private OldMenuPathMapper oldMenuPathMapper;


    @Override
    public void updateByKey(OldMenuPath oldMenuPath, String loginUserName, Integer loginUserId) {
        oldMenuPath.setUpdateTime(LocalDateTime.now());
        oldMenuPath.setUpdateUser(loginUserName);
        oldMenuPath.setUpdateUserId(loginUserId);
        ResultAssertHolder.apiAssert().isFalse(this.updateById(oldMenuPath), ResultEnum.UPDATE_FAIL);
    }

    @Override
    public void insert(OldMenuPath oldMenuPath, String loginUserName, Integer loginUserId) {
        oldMenuPath.setCreateTime(LocalDateTime.now());
        oldMenuPath.setCreateUser(loginUserName);
        oldMenuPath.setCreateUserId(loginUserId);
        ResultAssertHolder.apiAssert().isFalse(this.save(oldMenuPath), ResultEnum.SAVE_FAIL);
    }

    @Override
    public void removeById(Integer id, String loginUserName, Integer loginUserId) {
        OldMenuPath oldMenuPath = new OldMenuPath();
        oldMenuPath.setUpdateTime(LocalDateTime.now());
        oldMenuPath.setUpdateUser(loginUserName);
        oldMenuPath.setUpdateUserId(loginUserId);
        oldMenuPath.setDeleteFlag(Boolean.TRUE);
        ResultAssertHolder.apiAssert().isFalse(this.updateById(oldMenuPath), ResultEnum.DELETE_FAIL);
    }

}
