package com.old.business.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldNotice;
import com.old.common.mybatis.base.BaseService;

import java.util.List;

/**
 * 通知公告表  old_notice
 *
 * @author old
 * @date 2023-05-20
 */
public interface OldNoticeService extends BaseService<OldNotice> {


    Page<OldNotice> page(Integer pageNum, Integer pageSize, OldNotice oldNotice);

    List<OldNotice> list(OldNotice oldNotice);

    OldNotice getById(Integer id);

    void updateById(OldNotice oldNotice, Integer userId, String userName);

    void save(OldNotice oldNotice, Integer userId, String userName);

    void removeById(Integer id, Integer userId, String userName);

    void removeByIds(List<Integer> ids, Integer userId, String userName);
}
