package com.old.business.user.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.old.business.user.domain.OldUserRoleRel;
import com.old.business.user.mapper.OldUserRoleRelMapper;
import com.old.business.user.service.OldUserRoleRelService;
import com.old.common.enums.ResultEnum;
import com.old.common.mybatis.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户角色关联表  old_user_role_rel
 * <p>
 * mybatis-plus 提供了 baseMapper 所以这里不再注入
 *
 * @author oldX
 * @date 2023-05-14
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class OldUserRoleRelServiceImpl extends BaseServiceImpl<OldUserRoleRelMapper, OldUserRoleRel> implements OldUserRoleRelService {


    @Override
    public void insertAuthUsers(Integer roleId, List<Integer> userIds, Integer createUserId, String createUser) {
        LocalDateTime now = LocalDateTime.now();
        List<OldUserRoleRel> relList = userIds.stream().map(userId -> {
            OldUserRoleRel rel = new OldUserRoleRel();
            rel.setRoleId(roleId);
            rel.setUserId(userId);
            rel.setCreateUser(createUser);
            rel.setCreateUserId(createUserId);
            rel.setCreateTime(now);
            return rel;
        }).toList();
        apiAssert.isFalse(this.saveBatch(relList), ResultEnum.SAVE_FAIL);
    }

    @Override
    public void saveUserRoleRel(Integer userId, List<Integer> roleIds, Integer updateUserId, String updateUser) {
        LocalDateTime now = LocalDateTime.now();
        List<OldUserRoleRel> relList = roleIds.stream().map(roleId -> {
            OldUserRoleRel temp = new OldUserRoleRel();
            temp.setUserId(userId);
            temp.setRoleId(roleId);
            temp.setCreateUser(updateUser);
            temp.setCreateUserId(updateUserId);
            temp.setCreateTime(now);
            return temp;
        }).toList();
        apiAssert.isFalse(this.saveBatch(relList), ResultEnum.SAVE_FAIL);
    }


    @Override
    public void deleteAfterInsert(Integer userId, List<Integer> roleIds, Integer updateUserId, String updateUser) {
        Long count = this.lambdaQuery()
                .eq(OldUserRoleRel::getUserId, userId)
                .count();
        if (count > 0) {
            apiAssert.isFalse(this.lambdaUpdate()
                    .eq(OldUserRoleRel::getUserId, userId)
                    .remove(), ResultEnum.DELETE_FAIL);
        }

        if (CollUtil.isEmpty(roleIds)) {
            return;
        }
        this.saveUserRoleRel(userId, roleIds, updateUserId, updateUser);
    }

    @Override
    public void save(OldUserRoleRel oldUserRoleRel, Integer userId, String userName) {
        oldUserRoleRel.setCreateTime(LocalDateTime.now());
        oldUserRoleRel.setCreateUser(userName);
        oldUserRoleRel.setCreateUserId(userId);
        this.apiAssert.isFalse(this.save(oldUserRoleRel), ResultEnum.SAVE_FAIL);
    }

}
