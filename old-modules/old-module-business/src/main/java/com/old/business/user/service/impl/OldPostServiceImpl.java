package com.old.business.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldPost;
import com.old.business.user.domain.OldUserPost;
import com.old.business.user.mapper.OldPostMapper;
import com.old.business.user.service.OldPostService;
import com.old.business.user.service.OldUserPostService;
import com.old.common.enums.ResultEnum;
import com.old.common.mybatis.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 岗位信息表  old_post
 * <p>
 * mybatis-plus 提供了 baseMapper 所以这里不再注入
 *
 * @author old
 * @date 2023-05-20
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class OldPostServiceImpl extends BaseServiceImpl<OldPostMapper, OldPost> implements OldPostService {

    @Autowired
    OldUserPostService oldUserPostService;


    @Override
    public List<OldPost> selectByUserId(Integer userId) {
        List<Integer> ids = oldUserPostService.selectByUserId(userId).stream().map(OldUserPost::getPostId).toList();
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        return this.lambdaQuery()
                .in(OldPost::getId, ids)
                .eq(OldPost::getDeleteFlag, false)
                .list();
    }

    @Override
    public Page<OldPost> page(Integer pageNum, Integer pageSize, OldPost oldPost) {
        if (oldPost == null) {
            oldPost = new OldPost();
        }

        if (oldPost.getDeleteFlag() == null) {
            oldPost.setDeleteFlag(false);
        }
        return this.page(new Page<>(pageNum, pageSize), Wrappers.lambdaQuery(oldPost));
    }

    @Override
    public List<OldPost> list(OldPost oldPost) {
        if (oldPost == null) {
            oldPost = new OldPost();
        }

        if (oldPost.getDeleteFlag() == null) {
            oldPost.setDeleteFlag(false);
        }
        return this.list(Wrappers.lambdaQuery(oldPost));
    }


    @Override
    public OldPost getById(Integer id) {
        var byId = super.getById(id);
        if (byId == null || byId.getDeleteFlag()) {
            byId = null;
        }
        return byId;
    }

    @Override
    public void updateById(OldPost oldPost, Integer userId, String userName) {
        oldPost.setUpdateTime(LocalDateTime.now());
        oldPost.setUpdateUser(userName);
        oldPost.setUpdateUserId(userId);
        this.apiAssert.isFalse(this.updateById(oldPost), ResultEnum.UPDATE_FAIL);
    }

    @Override
    public void save(OldPost oldPost, Integer userId, String userName) {
        oldPost.setCreateTime(LocalDateTime.now());
        oldPost.setCreateUser(userName);
        oldPost.setCreateUserId(userId);
        this.apiAssert.isFalse(this.save(oldPost), ResultEnum.SAVE_FAIL);
    }

    @Override
    public void removeById(Integer id, Integer userId, String userName) {
        OldPost oldPost = new OldPost();
        oldPost.setId(id);
        oldPost.setUpdateTime(LocalDateTime.now());
        oldPost.setUpdateUser(userName);
        oldPost.setUpdateUserId(userId);
        oldPost.setDeleteFlag(Boolean.TRUE);
        this.apiAssert.isFalse(this.updateById(oldPost), ResultEnum.DELETE_FAIL);
    }


    @Override
    public void removeByIds(List<Integer> ids, Integer userId, String userName) {
        LocalDateTime now = LocalDateTime.now();
        List<OldPost> list = ids.stream().map(id -> {
            OldPost oldPost = new OldPost();
            oldPost.setId(id);
            oldPost.setUpdateTime(now);
            oldPost.setUpdateUser(userName);
            oldPost.setUpdateUserId(userId);
            oldPost.setDeleteFlag(Boolean.TRUE);
            return oldPost;
        }).toList();
        this.apiAssert.isFalse(this.updateBatchById(list), ResultEnum.DELETE_FAIL);
    }


}
