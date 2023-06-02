package com.old.business.user.service;

import com.old.business.user.domain.OldDept;
import com.old.business.user.domain.req.DeptTreeResp;
import com.old.common.mybatis.base.BaseService;

import java.util.List;

/**
 * 部门表  old_dept
 *
 * @author old
 * @date 2023-04-11
 */
public interface OldDeptService extends BaseService<OldDept> {

    List<DeptTreeResp> deptTree();

    OldDept getById(Integer id);

    void updateById(OldDept oldDept, Integer userId, String userName);

    void save(OldDept oldDept, Integer userId, String userName);

    void removeById(Integer id, Integer userId, String userName);

    void removeByIds(List<Integer> ids, Integer userId, String userName);

    void checkDeptNameUnique(Integer id, String deptName, Integer parentId);

    void insertDept(OldDept oldDept, Integer createUserId, String createUserName);
}
