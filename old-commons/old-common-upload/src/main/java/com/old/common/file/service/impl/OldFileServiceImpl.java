package com.old.common.file.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.common.enums.ResultEnum;
import com.old.common.file.domain.OldFile;
import com.old.common.file.mapper.OldFileMapper;
import com.old.common.file.service.OldFileService;
import com.old.common.mybatis.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * old_file
 * <p>
 * mybatis-plus 提供了 baseMapper 所以这里不再注入
 *
 * @author old
 * @date 2023-05-26
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class OldFileServiceImpl extends BaseServiceImpl<OldFileMapper, OldFile> implements OldFileService {


    @Override
    public List<OldFile> selectInId(Collection<Integer> ids) {
        if (CollUtil.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return this.lambdaQuery().in(OldFile::getId, ids).list();
    }

    @Override
    public Page<OldFile> page(Integer pageNum, Integer pageSize, OldFile oldFile) {
        if (oldFile == null) {
            oldFile = new OldFile();
        }

        if (oldFile.getDeleteFlag() == null) {
            oldFile.setDeleteFlag(false);
        }
        return this.page(new Page<>(pageNum, pageSize), Wrappers.lambdaQuery(oldFile));
    }

    @Override
    public List<OldFile> list(OldFile oldFile) {
        if (oldFile == null) {
            oldFile = new OldFile();
        }

        if (oldFile.getDeleteFlag() == null) {
            oldFile.setDeleteFlag(false);
        }
        return this.list(Wrappers.lambdaQuery(oldFile));
    }


    @Override
    public OldFile getById(Integer id) {
        var byId = super.getById(id);
        if (byId == null || byId.getDeleteFlag()) {
            byId = null;
        }
        return byId;
    }

    @Override
    public void updateById(OldFile oldFile, Integer userId, String userName) {
        oldFile.setUpdateTime(LocalDateTime.now());
        oldFile.setUpdateUser(userName);
        oldFile.setUpdateUserId(userId);
        this.apiAssert.isFalse(this.updateById(oldFile), ResultEnum.UPDATE_FAIL);
    }

    @Override
    public void save(OldFile oldFile, Integer userId, String userName) {
        oldFile.setCreateTime(LocalDateTime.now());
        oldFile.setCreateUser(userName);
        oldFile.setCreateUserId(userId);
        this.apiAssert.isFalse(this.save(oldFile), ResultEnum.SAVE_FAIL);
    }

    @Override
    public void removeById(Integer id, Integer userId, String userName) {
        OldFile oldFile = new OldFile();
        oldFile.setId(id);
        oldFile.setUpdateTime(LocalDateTime.now());
        oldFile.setUpdateUser(userName);
        oldFile.setUpdateUserId(userId);
        oldFile.setDeleteFlag(Boolean.TRUE);
        this.apiAssert.isFalse(this.updateById(oldFile), ResultEnum.DELETE_FAIL);
    }


    @Override
    public void removeByIds(List<Integer> ids, Integer userId, String userName) {
        LocalDateTime now = LocalDateTime.now();
        List<OldFile> list = ids.stream().map(id -> {
            OldFile oldFile = new OldFile();
            oldFile.setId(id);
            oldFile.setUpdateTime(now);
            oldFile.setUpdateUser(userName);
            oldFile.setUpdateUserId(userId);
            oldFile.setDeleteFlag(Boolean.TRUE);
            return oldFile;
        }).toList();
        this.apiAssert.isFalse(this.updateBatchById(list), ResultEnum.DELETE_FAIL);
    }


}
