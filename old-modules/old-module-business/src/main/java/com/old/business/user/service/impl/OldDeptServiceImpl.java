package com.old.business.user.service.impl;


import com.old.business.user.domain.OldDept;
import com.old.business.user.domain.req.DeptTreeResp;
import com.old.business.user.mapper.OldDeptMapper;
import com.old.business.user.service.OldDeptService;
import com.old.common.enums.ResultEnum;
import com.old.common.mybatis.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门表  old_dept
 * <p>
 * mybatis-plus 提供了 baseMapper 所以这里不再注入
 *
 * @author old
 * @date 2023-04-11
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class OldDeptServiceImpl extends BaseServiceImpl<OldDeptMapper, OldDept> implements OldDeptService {


    @Override
    public List<DeptTreeResp> deptTree() {
        List<OldDept> list = this.list(lqw().eq(OldDept::getDeleteFlag, false));
        return buildTree(list, BigDecimal.ZERO.intValue());
    }

    private List<DeptTreeResp> buildTree(List<OldDept> deptList, int parentId) {
        List<DeptTreeResp> parentList = deptList.stream().filter(oldDept -> parentId == oldDept.getParentId())
                .map(oldDept -> {
                    DeptTreeResp resp = new DeptTreeResp();
                    resp.setId(oldDept.getId());
                    resp.setLabel(oldDept.getDeptName());
                    return resp;
                }).toList();

        for (DeptTreeResp resp : parentList) {
            List<DeptTreeResp> child = buildTree(deptList.stream().filter(oldDept -> parentId != oldDept.getParentId()).toList(), resp.getId());
            resp.setChildren(child);
        }

        return parentList;
    }

    @Override
    public OldDept getById(Integer id) {
        var byId = super.getById(id);
        if (byId == null || byId.getDeleteFlag()) {
            byId = null;
        }
        return byId;
    }

    @Override
    public void updateById(OldDept oldDept, Integer userId, String userName) {
        oldDept.setUpdateTime(LocalDateTime.now());
        oldDept.setUpdateUser(userName);
        oldDept.setUpdateUserId(userId);
        apiAssert().isFalse(this.updateById(oldDept), ResultEnum.UPDATE_FAIL);
    }

    @Override
    public void save(OldDept oldDept, Integer userId, String userName) {
        oldDept.setCreateTime(LocalDateTime.now());
        oldDept.setCreateUser(userName);
        oldDept.setCreateUserId(userId);
        apiAssert().isFalse(this.save(oldDept), ResultEnum.SAVE_FAIL);
    }

    @Override
    public void removeById(Integer id, Integer userId, String userName) {
        OldDept oldDept = new OldDept();
        oldDept.setId(id);
        oldDept.setUpdateTime(LocalDateTime.now());
        oldDept.setUpdateUser(userName);
        oldDept.setUpdateUserId(userId);
        oldDept.setDeleteFlag(Boolean.TRUE);
        apiAssert().isFalse(this.updateById(oldDept), ResultEnum.DELETE_FAIL);
    }


    @Override
    public void removeByIds(List<Integer> ids, Integer userId, String userName) {
        LocalDateTime now = LocalDateTime.now();
        List<OldDept> list = ids.stream().map(id -> {
            OldDept oldDept = new OldDept();
            oldDept.setId(id);
            oldDept.setUpdateTime(now);
            oldDept.setUpdateUser(userName);
            oldDept.setUpdateUserId(userId);
            oldDept.setDeleteFlag(Boolean.TRUE);
            return oldDept;
        }).toList();
        apiAssert().isFalse(this.updateBatchById(list), ResultEnum.DELETE_FAIL);
    }

    @Override
    public void checkDeptNameUnique(Integer id, String deptName, Integer parentId) {
        var one = this.lambdaQuery()
                .eq(OldDept::getDeptName, deptName)
                .eq(OldDept::getParentId, parentId)
                .eq(OldDept::getDeleteFlag, false)
                .last("limit 1")
                .one();
        if (one != null) {
            apiAssert.isNull(id, ResultEnum.DEPT_NAME_NOT_UNIQUE)
                    .isFalse(one.getId().equals(id), ResultEnum.DEPT_NAME_NOT_UNIQUE);
        }
    }

    @Override
    public void insertDept(OldDept oldDept, Integer createUserId, String createUserName) {
        var info = this.getById(oldDept.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        apiAssert.isNull(info, ResultEnum.ILLEGAL_ACTION)
                .isTrue(info.getDeleteFlag(), ResultEnum.ILLEGAL_ACTION)
                .isFalse(info.getDeptStatus(), ResultEnum.DEPT_DISABLE);
        oldDept.setAncestors(info.getAncestors() + "," + oldDept.getParentId());
        this.save(oldDept, createUserId, createUserName);

    }


}
