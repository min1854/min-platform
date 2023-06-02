package com.old.business.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldPost;
import com.old.common.mybatis.base.BaseService;

import java.util.List;

/**
 * 岗位信息表  old_post
 *
 * @author old
 * @date 2023-05-20
 */
public interface OldPostService extends BaseService<OldPost> {

    List<OldPost> selectByUserId(Integer userId);

    Page<OldPost> page(Integer pageNum, Integer pageSize, OldPost oldPost);

    List<OldPost> list(OldPost oldPost);

    OldPost getById(Integer id);

    void updateById(OldPost oldPost, Integer userId, String userName);

    void save(OldPost oldPost, Integer userId, String userName);

    void removeById(Integer id, Integer userId, String userName);

    void removeByIds(List<Integer> ids, Integer userId, String userName);
}
