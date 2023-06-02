package com.old.business.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldNotice;
import com.old.business.user.mapper.OldNoticeMapper;
import com.old.business.user.service.OldNoticeService;
import com.old.common.enums.ResultEnum;
import com.old.common.mybatis.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知公告表  old_notice
 * <p>
 * mybatis-plus 提供了 baseMapper 所以这里不再注入
 *
 * @author old
 * @date 2023-05-20
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class OldNoticeServiceImpl extends BaseServiceImpl<OldNoticeMapper, OldNotice> implements OldNoticeService {


    @Override
    public Page<OldNotice> page(Integer pageNum, Integer pageSize, OldNotice oldNotice) {
        if (oldNotice == null) {
            oldNotice = new OldNotice();
        }

        if (oldNotice.getDeleteFlag() == null) {
            oldNotice.setDeleteFlag(false);
        }
        return this.page(new Page<>(pageNum, pageSize), Wrappers.lambdaQuery(oldNotice));
    }

    @Override
    public List<OldNotice> list(OldNotice oldNotice) {
        if (oldNotice == null) {
            oldNotice = new OldNotice();
        }

        if (oldNotice.getDeleteFlag() == null) {
            oldNotice.setDeleteFlag(false);
        }
        return this.list(Wrappers.lambdaQuery(oldNotice));
    }


    @Override
    public OldNotice getById(Integer id) {
        var byId = super.getById(id);
        if (byId == null || byId.getDeleteFlag()) {
            byId = null;
        }
        return byId;
    }

    @Override
    public void updateById(OldNotice oldNotice, Integer userId, String userName) {
        oldNotice.setUpdateTime(LocalDateTime.now());
        oldNotice.setUpdateUser(userName);
        oldNotice.setUpdateUserId(userId);
        this.apiAssert.isFalse(this.updateById(oldNotice), ResultEnum.UPDATE_FAIL);
    }

    @Override
    public void save(OldNotice oldNotice, Integer userId, String userName) {
        oldNotice.setCreateTime(LocalDateTime.now());
        oldNotice.setCreateUser(userName);
        oldNotice.setCreateUserId(userId);
        this.apiAssert.isFalse(this.save(oldNotice), ResultEnum.SAVE_FAIL);
    }

    @Override
    public void removeById(Integer id, Integer userId, String userName) {
        OldNotice oldNotice = new OldNotice();
        oldNotice.setId(id);
        oldNotice.setUpdateTime(LocalDateTime.now());
        oldNotice.setUpdateUser(userName);
        oldNotice.setUpdateUserId(userId);
        oldNotice.setDeleteFlag(Boolean.TRUE);
        this.apiAssert.isFalse(this.updateById(oldNotice), ResultEnum.DELETE_FAIL);
    }


    @Override
    public void removeByIds(List<Integer> ids, Integer userId, String userName) {
        LocalDateTime now = LocalDateTime.now();
        List<OldNotice> list = ids.stream().map(id -> {
            OldNotice oldNotice = new OldNotice();
            oldNotice.setId(id);
            oldNotice.setUpdateTime(now);
            oldNotice.setUpdateUser(userName);
            oldNotice.setUpdateUserId(userId);
            oldNotice.setDeleteFlag(Boolean.TRUE);
            return oldNotice;
        }).toList();
        this.apiAssert.isFalse(this.updateBatchById(list), ResultEnum.DELETE_FAIL);
    }


}
