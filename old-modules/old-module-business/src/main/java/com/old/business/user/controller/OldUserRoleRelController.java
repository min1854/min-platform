package com.old.business.user.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldUserRoleRel;
import com.old.business.user.service.OldUserRoleRelService;
import com.old.common.apiAssert.ResultAssertHolder;
import com.old.common.enums.ResultEnum;
import com.old.common.mybatis.result.DatabasePageR;
import com.old.common.result.PageData;
import com.old.common.result.R;
import com.old.common.web.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 用户角色关联表 Controller old_user_role_rel
 *
 * @author old
 * @date 2023-05-14
 */
@Slf4j
@RestController
@RequestMapping("/oldUserRoleRel")
public class OldUserRoleRelController {

    private static final ResultAssertHolder.ResultAssert apiAssert = ResultAssertHolder.apiAssert();

    @Autowired
    private OldUserRoleRelService oldUserRoleRelService;

    @PostMapping("/authUser/cancelAll")
    public R<Void> cancelAuthUserAll(@RequestParam("roleId") Integer roleId, @RequestParam("userIds") List<Integer> userIds) {
        boolean remove = oldUserRoleRelService.remove(oldUserRoleRelService.lqw().eq(OldUserRoleRel::getRoleId, roleId)
                .in(OldUserRoleRel::getUserId, userIds)
                .last("limit " + userIds.size()));
        apiAssert.isFalse(remove, ResultEnum.DELETE_FAIL);
        return R.ok();
    }

    @PostMapping("/authUser/cancel")
    public R<Void> cancelAuthUser(@RequestParam("userId") Integer userId, @RequestParam("roleId") Integer roleId) {
        boolean remove = oldUserRoleRelService.remove(oldUserRoleRelService.lqw().eq(OldUserRoleRel::getUserId, userId)
                .eq(OldUserRoleRel::getRoleId, roleId)
                .last("limit 1"));
        apiAssert.isFalse(remove, ResultEnum.DELETE_FAIL);
        return R.ok();
    }

    @GetMapping("/page")
    public R<PageData<OldUserRoleRel>> page(@ModelAttribute OldUserRoleRel oldUserRoleRel,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return DatabasePageR.of(oldUserRoleRelService.page(new Page<>(pageNum, pageSize), Wrappers.lambdaQuery(oldUserRoleRel)));
    }


    @GetMapping("/select")
    public R<List<OldUserRoleRel>> select(@ModelAttribute OldUserRoleRel oldUserRoleRel) {
        List<OldUserRoleRel> list = oldUserRoleRelService.list(Wrappers.lambdaQuery(oldUserRoleRel));
        return R.ok(list);
    }

    @PostMapping("/save")
    public R<Void> save(@RequestBody OldUserRoleRel oldUserRoleRel) {
        oldUserRoleRelService.save(oldUserRoleRel, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


}
