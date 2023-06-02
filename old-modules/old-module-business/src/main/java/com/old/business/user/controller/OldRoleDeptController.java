package com.old.business.user.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldRoleDept;
import com.old.business.user.service.OldRoleDeptService;
import com.old.common.apiAssert.ResultAssertHolder;
import com.old.common.mybatis.result.DatabasePageR;
import com.old.common.result.PageData;
import com.old.common.result.R;
import com.old.common.web.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 角色和部门关联表 Controller old_role_dept
 *
 * @author old
 * @date 2023-04-25
 */
@Slf4j
@RestController
@RequestMapping("/oldRoleDept")
public class OldRoleDeptController {
    private static final ResultAssertHolder.ResultAssert apiAssert = ResultAssertHolder.apiAssert();
    @Autowired
    OldRoleDeptService oldRoleDeptService;

    @GetMapping("/page")
    public R<PageData<OldRoleDept>> page(@ModelAttribute OldRoleDept oldRoleDept,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return DatabasePageR.of(oldRoleDeptService.page(new Page<>(pageNum, pageSize), Wrappers.lambdaQuery(oldRoleDept)));
    }


    @GetMapping("/select")
    public R<List<OldRoleDept>> select(@ModelAttribute OldRoleDept oldRoleDept) {
        List<OldRoleDept> list = oldRoleDeptService.list(Wrappers.lambdaQuery(oldRoleDept));
        return R.ok(list);
    }


    @PostMapping("/save")
    public R<Void> save(@RequestBody OldRoleDept oldRoleDept) {
        oldRoleDeptService.save(oldRoleDept, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


}
