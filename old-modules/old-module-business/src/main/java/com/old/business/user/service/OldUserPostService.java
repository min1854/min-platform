package com.old.business.user.service;

import com.old.business.user.domain.OldUserPost;
import com.old.common.mybatis.base.BaseService;

import java.util.List;

/**
 * 用户与岗位关联表  old_user_post
 *
 * @author old
 * @date 2023-04-13
 */
public interface OldUserPostService extends BaseService<OldUserPost> {

    void updateById(OldUserPost oldUserPost, Integer userId, String userName);

    void save(OldUserPost oldUserPost, Integer userId, String userName);

    void removeById(Integer id, Integer userId, String userName);

    void removeByIds(List<Integer> ids, Integer userId, String userName);

    List<OldUserPost> selectByUserId(Integer id);

    void saveUserPost(Integer userId, List<Integer> postIds, Integer updateUserId, String updateUser);

    void deleteAfterInsert(Integer id, List<Integer> postIds, Integer updateUserId, String updateUser);
}
