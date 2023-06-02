package com.old.business.user.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldRoleMenuRel;
import com.old.business.user.service.OldRoleMenuRelService;
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
 * 角色菜单关联表 Controller old_role_menu_rel
 *
 * @author old
 * @date 2023-04-19
 */
@Slf4j
@RestController
@RequestMapping("/oldRoleMenuRel")
public class OldRoleMenuRelController {
    private static final ResultAssertHolder.ResultAssert apiAssert = ResultAssertHolder.apiAssert();
    @Autowired
    OldRoleMenuRelService oldRoleMenuRelService;

    @GetMapping("/page")
    public R<PageData<OldRoleMenuRel>> page(@ModelAttribute OldRoleMenuRel oldRoleMenuRel,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return DatabasePageR.of(oldRoleMenuRelService.page(new Page<>(pageNum, pageSize), Wrappers.lambdaQuery(oldRoleMenuRel)));
    }

    @GetMapping("/select")
    public R<List<OldRoleMenuRel>> select(@ModelAttribute OldRoleMenuRel oldRoleMenuRel) {
        List<OldRoleMenuRel> list = oldRoleMenuRelService.list(Wrappers.lambdaQuery(oldRoleMenuRel));
        return R.ok(list);
    }

    @PostMapping("/save")
    public R<Void> save(@RequestBody OldRoleMenuRel oldRoleMenuRel) {
        oldRoleMenuRelService.save(oldRoleMenuRel, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


}
