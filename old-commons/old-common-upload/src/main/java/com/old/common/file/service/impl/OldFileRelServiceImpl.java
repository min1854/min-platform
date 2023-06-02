package com.old.common.file.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.common.enums.ResultEnum;
import com.old.common.file.domain.OldFile;
import com.old.common.file.domain.OldFileRel;
import com.old.common.file.enums.OldFileRelEnums;
import com.old.common.file.mapper.OldFileRelMapper;
import com.old.common.file.service.OldFileRelService;
import com.old.common.file.service.OldFileService;
import com.old.common.mybatis.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件引用表  old_file_rel
 * <p>
 * mybatis-plus 提供了 baseMapper 所以这里不再注入
 *
 * @author old
 * @date 2023-05-26
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class OldFileRelServiceImpl extends BaseServiceImpl<OldFileRelMapper, OldFileRel> implements OldFileRelService {

    @Autowired
    private OldFileService oldFileService;

    @Override
    public List<OldFile> selectFileByRelIdAndRelType(Integer relId, OldFileRelEnums.RelType relType) {
        List<Integer> fileIds = selectFileIdByRelIdAndRelType(relId, relType);
        if (fileIds == null || fileIds.isEmpty()) {
            return new ArrayList<>();
        }
        return oldFileService.selectInId(fileIds);
    }

    public List<Integer> selectFileIdByRelIdAndRelType(Integer relId, OldFileRelEnums.RelType relType) {
        return this.lambdaQuery()
                .select(OldFileRel::getFileId)
                .eq(OldFileRel::getRelId, relId)
                .eq(OldFileRel::getRelType, relType)
                .list()
                .stream()
                .map(OldFileRel::getFileId).toList();
    }


    @Override
    public Page<OldFileRel> page(Integer pageNum, Integer pageSize, OldFileRel oldFileRel) {
        if (oldFileRel == null) {
            oldFileRel = new OldFileRel();
        }

        if (oldFileRel.getDeleteFlag() == null) {
            oldFileRel.setDeleteFlag(false);
        }
        return this.page(new Page<>(pageNum, pageSize), Wrappers.lambdaQuery(oldFileRel));
    }

    @Override
    public List<OldFileRel> list(OldFileRel oldFileRel) {
        if (oldFileRel == null) {
            oldFileRel = new OldFileRel();
        }

        if (oldFileRel.getDeleteFlag() == null) {
            oldFileRel.setDeleteFlag(false);
        }
        return this.list(Wrappers.lambdaQuery(oldFileRel));
    }


    @Override
    public OldFileRel getById(Integer id) {
        var byId = super.getById(id);
        if (byId == null || byId.getDeleteFlag()) {
            byId = null;
        }
        return byId;
    }

    @Override
    public void updateById(OldFileRel oldFileRel, Integer userId, String userName) {
        oldFileRel.setUpdateTime(LocalDateTime.now());
        oldFileRel.setUpdateUser(userName);
        oldFileRel.setUpdateUserId(userId);
        this.apiAssert.isFalse(this.updateById(oldFileRel), ResultEnum.UPDATE_FAIL);
    }

    @Override
    public void save(OldFileRel oldFileRel, Integer userId, String userName) {
        oldFileRel.setCreateTime(LocalDateTime.now());
        oldFileRel.setCreateUser(userName);
        oldFileRel.setCreateUserId(userId);
        this.apiAssert.isFalse(this.save(oldFileRel), ResultEnum.SAVE_FAIL);
    }

    @Override
    public void removeById(Integer id, Integer userId, String userName) {
        OldFileRel oldFileRel = new OldFileRel();
        oldFileRel.setId(id);
        oldFileRel.setUpdateTime(LocalDateTime.now());
        oldFileRel.setUpdateUser(userName);
        oldFileRel.setUpdateUserId(userId);
        oldFileRel.setDeleteFlag(Boolean.TRUE);
        this.apiAssert.isFalse(this.updateById(oldFileRel), ResultEnum.DELETE_FAIL);
    }


    @Override
    public void removeByIds(List<Integer> ids, Integer userId, String userName) {
        LocalDateTime now = LocalDateTime.now();
        List<OldFileRel> list = ids.stream().map(id -> {
            OldFileRel oldFileRel = new OldFileRel();
            oldFileRel.setId(id);
            oldFileRel.setUpdateTime(now);
            oldFileRel.setUpdateUser(userName);
            oldFileRel.setUpdateUserId(userId);
            oldFileRel.setDeleteFlag(Boolean.TRUE);
            return oldFileRel;
        }).toList();
        this.apiAssert.isFalse(this.updateBatchById(list), ResultEnum.DELETE_FAIL);
    }


}
