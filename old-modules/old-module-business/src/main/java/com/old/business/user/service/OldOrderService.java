package com.old.business.user.service;

import com.old.business.user.domain.OldOrder;
import com.old.common.mybatis.base.BaseService;

/**
 * 订单表  old_order
 *
 * @author old
 * @date 2022-10-17
 */
public interface OldOrderService extends BaseService<OldOrder> {

    void updateByKey(OldOrder oldOrder, String loginUserName, Integer loginUserId);

    void insert(OldOrder oldOrder, String loginUserName, Integer loginUserId);

    void removeById(Integer id, String loginUserName, Integer loginUserId);
}
