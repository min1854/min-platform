package com.old.business.user.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldDept;
import com.old.business.user.domain.req.DeptTreeResp;
import com.old.business.user.service.OldDeptService;
import com.old.common.mybatis.result.DatabasePageR;
import com.old.common.result.PageData;
import com.old.common.result.R;
import com.old.common.web.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 部门表 Controller old_dept
 *
 * @author old
 * @date 2023-04-11
 */
@Slf4j
@RestController
@RequestMapping("/oldDept")
public class OldDeptController {
    @Autowired
    OldDeptService oldDeptService;

    @GetMapping("/list/exclude/{deptId}")
    public R<List<OldDept>> excludeChild(@PathVariable(value = "deptId", required = false) Integer deptId) {
        var depts = oldDeptService.list(oldDeptService.lqw().eq(OldDept::getDeleteFlag, false));
        depts.removeIf(d -> d.getId().intValue() == deptId || Arrays.stream(d.getAncestors().split(",")).toList().contains(deptId));
        return R.ok(depts);
    }

    @GetMapping("/viewDataList")
    public R<List<OldDept>> viewDataList(@RequestParam(value = "deptName", required = false) String deptName,
                                         @RequestParam(value = "deptStatus", required = false) Boolean deptStatus) {
        return R.ok(oldDeptService.list(oldDeptService.lqw()
                .like(StrUtil.isNotBlank(deptName), OldDept::getDeptName, deptName)
                .eq(deptStatus != null, OldDept::getDeptStatus, deptStatus)
                .eq(OldDept::getDeleteFlag, false)));
    }

    @PostMapping("/insertDept")
    public R<Void> insertDept(@RequestBody OldDept oldDept) {
        oldDeptService.checkDeptNameUnique(oldDept.getId(), oldDept.getDeptName(), oldDept.getParentId());
        oldDeptService.insertDept(oldDept, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

    @GetMapping("/deptTree")
    public R<List<DeptTreeResp>> deptTree() {
        return R.ok(oldDeptService.deptTree());
    }

    @GetMapping("/page")
    public R<PageData<OldDept>> page(@ModelAttribute OldDept oldDept,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return DatabasePageR.of(oldDeptService.page(new Page<>(pageNum, pageSize), Wrappers.lambdaQuery(oldDept)));
    }

    @GetMapping("/select")
    public R<List<OldDept>> select(@ModelAttribute OldDept oldDept) {
        List<OldDept> list = oldDeptService.list(Wrappers.lambdaQuery(oldDept));
        return R.ok(list);
    }

    @GetMapping("/getById")
    public R<OldDept> getById(@RequestParam("id") Integer id) {
        return R.ok(oldDeptService.getById(id));
    }

    @PostMapping("/updateById")
    public R<Void> updateById(@RequestBody OldDept oldDept) {
        oldDeptService.updateById(oldDept, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

    @PostMapping("/save")
    public R<Void> save(@RequestBody OldDept oldDept) {
        oldDeptService.save(oldDept, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

    @PostMapping("/removeById")
    public R<Void> removeById(@RequestParam("id") Integer id) {
        oldDeptService.removeById(id, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


    @PostMapping("/removeByIds")
    public R<Void> removeByIds(@RequestParam("ids") List<Integer> ids) {
        oldDeptService.removeByIds(ids, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

}
