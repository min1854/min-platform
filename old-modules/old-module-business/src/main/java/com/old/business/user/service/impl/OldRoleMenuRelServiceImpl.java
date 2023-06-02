package com.old.business.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.old.business.user.domain.OldRoleMenuRel;
import com.old.business.user.mapper.OldRoleMenuRelMapper;
import com.old.business.user.service.OldRoleMenuRelService;
import com.old.common.enums.ResultEnum;
import com.old.common.mybatis.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色菜单关联表  old_role_menu_rel
 * <p>
 * mybatis-plus 提供了 baseMapper 所以这里不再注入
 *
 * @author old
 * @date 2023-04-19
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class OldRoleMenuRelServiceImpl extends BaseServiceImpl<OldRoleMenuRelMapper, OldRoleMenuRel> implements OldRoleMenuRelService {


    @Override
    public void save(OldRoleMenuRel oldRoleMenuRel, Integer userId, String userName) {
        oldRoleMenuRel.setCreateTime(LocalDateTime.now());
        oldRoleMenuRel.setCreateUser(userName);
        oldRoleMenuRel.setCreateUserId(userId);
        this.apiAssert.isFalse(this.save(oldRoleMenuRel), ResultEnum.SAVE_FAIL);
    }


    @Override
    public void removeByRoleId(Integer roleId) {
        LambdaQueryWrapper<OldRoleMenuRel> wrapper = this.lqw().eq(OldRoleMenuRel::getRoleId, roleId);
        long count = this.count(wrapper);
        log.debug("根据角色id删除菜单关联：{}，{}", roleId, count);
        if (count <= 0) {
            return;
        }
        boolean remove = this.remove(wrapper);
        apiAssert.isFalse(remove, ResultEnum.DELETE_FAIL);
    }

    @Override
    public void removeByRoleIdAndInsert(Integer roleId, List<Integer> menuIds, Integer operateUserId, String operateUserName) {
        this.removeByRoleId(roleId);

        if (menuIds == null || menuIds.size() == 0) {
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        List<OldRoleMenuRel> insetList = menuIds.stream().map(id -> {
            var rel = new OldRoleMenuRel();
            rel.setMenuId(id);
            rel.setRoleId(roleId);
            rel.setCreateTime(now);
            rel.setCreateUser(operateUserName);
            rel.setCreateUserId(operateUserId);
            return rel;
        }).toList();
        boolean saveBatch = this.baseMapper.saveBatch(insetList) >= insetList.size();
        apiAssert.isFalse(saveBatch, ResultEnum.SAVE_FAIL);


    }


}
