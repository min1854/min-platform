package com.old.common.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.common.file.domain.OldFile;
import com.old.common.file.domain.OldFileRel;
import com.old.common.file.enums.OldFileRelEnums;
import com.old.common.mybatis.base.BaseService;

import java.util.List;

/**
 * 文件引用表  old_file_rel
 *
 * @author old
 * @date 2023-05-26
 */
public interface OldFileRelService extends BaseService<OldFileRel> {

    List<OldFile> selectFileByRelIdAndRelType(Integer relId, OldFileRelEnums.RelType relType);

    Page<OldFileRel> page(Integer pageNum, Integer pageSize, OldFileRel oldFileRel);

    List<OldFileRel> list(OldFileRel oldFileRel);

    OldFileRel getById(Integer id);

    void updateById(OldFileRel oldFileRel, Integer userId, String userName);

    void save(OldFileRel oldFileRel, Integer userId, String userName);

    void removeById(Integer id, Integer userId, String userName);

    void removeByIds(List<Integer> ids, Integer userId, String userName);
}
