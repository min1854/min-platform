package com.old.common.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.common.file.domain.OldFile;
import com.old.common.mybatis.base.BaseService;

import java.util.Collection;
import java.util.List;

/**
 * old_file
 *
 * @author old
 * @date 2023-05-26
 */
public interface OldFileService extends BaseService<OldFile> {

    List<OldFile> selectInId(Collection<Integer> ids);

    Page<OldFile> page(Integer pageNum, Integer pageSize, OldFile oldFile);

    List<OldFile> list(OldFile oldFile);

    OldFile getById(Integer id);

    void updateById(OldFile oldFile, Integer userId, String userName);

    void save(OldFile oldFile, Integer userId, String userName);

    void removeById(Integer id, Integer userId, String userName);

    void removeByIds(List<Integer> ids, Integer userId, String userName);
}
