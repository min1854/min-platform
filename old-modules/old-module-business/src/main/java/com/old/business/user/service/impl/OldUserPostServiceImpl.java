package com.old.business.user.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.old.business.user.domain.OldUserPost;
import com.old.business.user.mapper.OldUserPostMapper;
import com.old.business.user.service.OldUserPostService;
import com.old.common.enums.ResultEnum;
import com.old.common.mybatis.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户与岗位关联表  old_user_post
 * <p>
 * mybatis-plus 提供了 baseMapper 所以这里不再注入
 *
 * @author old
 * @date 2023-04-13
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class OldUserPostServiceImpl extends BaseServiceImpl<OldUserPostMapper, OldUserPost> implements OldUserPostService {

    @Override
    public void updateById(OldUserPost oldUserPost, Integer userId, String userName) {
        oldUserPost.setUpdateTime(LocalDateTime.now());
        oldUserPost.setUpdateUser(userName);
        oldUserPost.setUpdateUserId(userId);
        apiAssert().isFalse(this.updateById(oldUserPost), ResultEnum.UPDATE_FAIL);
    }

    @Override
    public void save(OldUserPost oldUserPost, Integer userId, String userName) {
        oldUserPost.setCreateTime(LocalDateTime.now());
        oldUserPost.setCreateUser(userName);
        oldUserPost.setCreateUserId(userId);
        apiAssert().isFalse(this.save(oldUserPost), ResultEnum.SAVE_FAIL);
    }

    @Override
    public void removeById(Integer id, Integer userId, String userName) {
        OldUserPost oldUserPost = new OldUserPost();
        oldUserPost.setId(id);
        oldUserPost.setUpdateTime(LocalDateTime.now());
        oldUserPost.setUpdateUser(userName);
        oldUserPost.setUpdateUserId(userId);
        oldUserPost.setDeleteFlag(Boolean.TRUE);
        apiAssert().isFalse(this.updateById(oldUserPost), ResultEnum.DELETE_FAIL);
    }


    @Override
    public void removeByIds(List<Integer> ids, Integer userId, String userName) {
        LocalDateTime now = LocalDateTime.now();
        List<OldUserPost> list = ids.stream().map(id -> {
            OldUserPost oldUserPost = new OldUserPost();
            oldUserPost.setId(id);
            oldUserPost.setUpdateTime(now);
            oldUserPost.setUpdateUser(userName);
            oldUserPost.setUpdateUserId(userId);
            oldUserPost.setDeleteFlag(Boolean.TRUE);
            return oldUserPost;
        }).toList();
        apiAssert().isFalse(this.updateBatchById(list), ResultEnum.DELETE_FAIL);
    }

    @Override
    public List<OldUserPost> selectByUserId(Integer id) {
        return this.lambdaQuery().eq(OldUserPost::getUserId, id)
                .eq(OldUserPost::getDeleteFlag, false)
                .list();
    }

    @Override
    public void saveUserPost(Integer userId, List<Integer> postIds, Integer updateUserId, String updateUser) {
        LocalDateTime now = LocalDateTime.now();
        List<OldUserPost> oldUserPosts = postIds.stream().map(postId -> {
            OldUserPost temp = new OldUserPost();
            temp.setUserId(userId);
            temp.setPostId(postId);
            temp.setCreateUser(updateUser);
            temp.setCreateUserId(updateUserId);
            temp.setCreateTime(now);
            return temp;
        }).toList();
        apiAssert.isFalse(this.saveBatch(oldUserPosts), ResultEnum.SAVE_FAIL);
    }

    @Override
    public void deleteAfterInsert(Integer userId, List<Integer> postIds, Integer updateUserId, String updateUser) {

        int size = this.selectByUserId(userId).size();
        OldUserPost entity = new OldUserPost();
        entity.setDeleteFlag(true);
        entity.setUpdateUser(updateUser);
        entity.setUpdateUserId(updateUserId);
        LocalDateTime now = LocalDateTime.now();
        entity.setUpdateTime(now);
        apiAssert.isFalse(baseMapper.update(entity, lqw().eq(OldUserPost::getUserId, userId)) >= size, ResultEnum.DELETE_FAIL);
        if (CollUtil.isNotEmpty(postIds)) {
            saveUserPost(userId, postIds, updateUserId, updateUser);
        }
    }


}
