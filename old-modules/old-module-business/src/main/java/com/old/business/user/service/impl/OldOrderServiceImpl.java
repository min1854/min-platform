package com.old.business.user.service.impl;


import com.old.business.user.domain.OldOrder;
import com.old.business.user.mapper.OldOrderMapper;
import com.old.business.user.service.OldOrderService;
import com.old.common.apiAssert.ResultAssertHolder;
import com.old.common.enums.ResultEnum;
import com.old.common.mybatis.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 订单表  old_order
 *
 * @author old
 * @date 2022-10-17
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class OldOrderServiceImpl extends BaseServiceImpl<OldOrderMapper, OldOrder> implements OldOrderService {

    @Autowired
    private OldOrderMapper oldOrderMapper;


    @Override
    public void updateByKey(OldOrder oldOrder, String loginUserName, Integer loginUserId) {
        oldOrder.setUpdateTime(LocalDateTime.now());
        oldOrder.setUpdateUser(loginUserName);
        oldOrder.setUpdateUserId(loginUserId);
        ResultAssertHolder.apiAssert().isFalse(this.updateById(oldOrder), ResultEnum.UPDATE_FAIL);
    }

    @Override
    public void insert(OldOrder oldOrder, String loginUserName, Integer loginUserId) {
        oldOrder.setCreateTime(LocalDateTime.now());
        oldOrder.setCreateUser(loginUserName);
        oldOrder.setCreateUserId(loginUserId);
        ResultAssertHolder.apiAssert().isFalse(this.save(oldOrder), ResultEnum.SAVE_FAIL);
    }

    @Override
    public void removeById(Integer id, String loginUserName, Integer loginUserId) {
        OldOrder oldOrder = new OldOrder();
        oldOrder.setUpdateTime(LocalDateTime.now());
        oldOrder.setUpdateUser(loginUserName);
        oldOrder.setUpdateUserId(loginUserId);
        oldOrder.setDeleteFlag(Boolean.TRUE);
        ResultAssertHolder.apiAssert().isFalse(this.updateById(oldOrder), ResultEnum.DELETE_FAIL);
    }

}
