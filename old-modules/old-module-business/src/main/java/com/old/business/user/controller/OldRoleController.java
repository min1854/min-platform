package com.old.business.user.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldRole;
import com.old.business.user.domain.OldUser;
import com.old.business.user.domain.req.RoleDataScopeReq;
import com.old.business.user.domain.req.SearchRoleReq;
import com.old.business.user.domain.req.UpdateAndAllocationReq;
import com.old.business.user.domain.resp.RoleDeptTreeResp;
import com.old.business.user.service.OldDeptService;
import com.old.business.user.service.OldRoleService;
import com.old.business.user.service.OldUserRoleRelService;
import com.old.common.apiAssert.ResultAssertHolder;
import com.old.common.domain.login.LoginUser;
import com.old.common.domain.login.RoleBo;
import com.old.common.enums.ResultEnum;
import com.old.common.mybatis.result.DatabasePageR;
import com.old.common.mybatis.result.DbPageR;
import com.old.common.result.PageData;
import com.old.common.result.R;
import com.old.common.web.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.old.common.apiAssert.ResultAssertHolder.apiAssert;


/**
 * 角色表 Controller old_role
 *
 * @author old
 * @date 2023-04-01
 */
@Slf4j
@RestController
@RequestMapping("/oldRole")
public class OldRoleController {

    private static final ResultAssertHolder.ResultAssert apiAssert = ResultAssertHolder.apiAssert();

    @Autowired
    OldRoleService oldRoleService;

    @Autowired
    OldDeptService oldDeptService;

    @Autowired
    OldUserRoleRelService oldUserRoleRelService;

    @PostMapping("/removeByIdAndCheckPower")
    public R<Void> removeByIdAndCheckPower(@RequestBody List<Integer> ids) {
        ids.forEach(id -> {
            apiAssert.isTrue(RoleBo.ADMIN_ID.equals(id), ResultEnum.ADMIN_NOT_ALLOWED_OPERATION);
        });
        oldRoleService.removeByIds(ids, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

    @PutMapping("/authUser/selectAll")
    public R<Void> selectAuthUserAll(@RequestParam("roleId") Integer roleId, @RequestParam("userIds") List<Integer> userIds) {
        LoginUser loginUser = LoginUtil.getLoginUser();
        oldRoleService.checkRoleDataScope(loginUser.loginUserId(), LoginUser.DEFAULT_USER_ID, loginUser.roleIds(), roleId);
        oldUserRoleRelService.insertAuthUsers(roleId, userIds, loginUser.loginUserId(), loginUser.loginUserName());
        return R.ok();
    }

    @GetMapping("/selectUnallocatedList")
    public R<PageData<OldUser>> selectUnallocatedList(@RequestParam(value = "roleId", required = false) Integer roleId,
                                                      @RequestParam(value = "userName", required = false) String userName,
                                                      @RequestParam(value = "phone", required = false) String phone,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return DbPageR.of(oldRoleService.selectUnallocatedList(roleId, userName, phone, pageNum, pageSize));
    }


    @GetMapping("/selectAllocatedList")
    public R<PageData<OldUser>> selectAllocatedList(@RequestParam(value = "roleId", required = false) Integer roleId,
                                                    @RequestParam(value = "userName", required = false) String userName,
                                                    @RequestParam(value = "phone", required = false) String phone,
                                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return DbPageR.of(oldRoleService.selectAllocatedList(roleId, userName, phone, pageNum, pageSize));
    }

    @PostMapping("/updateAndAllocation")
    public R<Void> updateAndAllocation(@Validated @RequestBody UpdateAndAllocationReq req) {
        Integer loginUserId = LoginUtil.loginUserId();
        oldRoleService.checkRoleAllowed(req.getId(), RoleBo.ADMIN_ID, loginUserId, LoginUser.DEFAULT_USER_ID);
        oldRoleService.checkRoleDataScope(loginUserId, LoginUser.DEFAULT_USER_ID, LoginUtil.getLoginUser().getRoleBos().stream().map(RoleBo::getId).toList(),
                req.getId());
        oldRoleService.checkRoleNameUnique(req.getId(), req.getRoleName());
        oldRoleService.checkRoleKeyUnique(req.getId(), req.getRoleKey());


        oldRoleService.updateAndAllocation(req, loginUserId, LoginUtil.loginUserName());

        // 更新缓存用户权限
        return R.ok();
    }

    @PostMapping("/updateDataScope")
    public R<Void> updateDataScope(@RequestBody RoleDataScopeReq req) {
        LoginUser loginUser = LoginUtil.getLoginUser();
        checkPower(loginUser, req.id());
        oldRoleService.updateDataScope(req, loginUser.loginUserId(), loginUser.loginUserName());
        return R.ok();
    }


    @GetMapping(value = "/deptTree/{id}")
    public R<RoleDeptTreeResp> deptTree(@PathVariable("id") Integer id) {
        OldRole oldRole = oldRoleService.getById(id);
        apiAssert().isNull(oldRole, ResultEnum.ROLE_NOT_EXISTS)
                .isTrue(oldRole.getDeleteFlag(), ResultEnum.ILLEGAL_ACTION);
        return R.ok(new RoleDeptTreeResp(oldRoleService.selectDeptListByRoleId(id, oldRole.getDeptCheckStrictly()),
                oldDeptService.deptTree()));
    }

    @PostMapping("/changeStatus")
    public R<Void> changeStatus(@RequestParam("id") Integer id, @RequestParam("roleStatus") Boolean roleStatus) {
        LoginUser loginUser = LoginUtil.getLoginUser();
        checkPower(loginUser, id);
        OldRole oldRole = new OldRole();
        oldRole.setId(id);
        oldRole.setRoleStatus(roleStatus);
        oldRoleService.updateById(oldRole, loginUser.loginUserId(), loginUser.loginUserName());
        return R.ok();

    }

    private void checkPower(LoginUser loginUser, Integer roleId) {
        apiAssert().isTrue(RoleBo.ADMIN_ID.equals(roleId), ResultEnum.NOT_ALLOWED_UPDATE_ADMIN_ROLE)
                .isTrue(!loginUser.isDefaultUser() && loginUser
                        .getRoleBos()
                        .stream()
                        .filter(roleBo -> roleId.equals(roleBo.getId()))
                        .findAny()
                        .isEmpty(), ResultEnum.NO_POWER_UPDATE_ROLE);
    }

    @GetMapping("/searchRolePage")
    public R<PageData<OldRole>> searchRolePage(@ModelAttribute SearchRoleReq req,
                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return DbPageR.of(oldRoleService.searchRolePage(req, pageNum, pageSize));
    }

    @GetMapping("/filterAdmin")
    public R<List<OldRole>> filterAdmin(@RequestParam(value = "isAdmin", defaultValue = "false") Boolean isAdmin) {
        return R.ok(oldRoleService.list(oldRoleService.lqw()
                .ne(isAdmin, OldRole::getId, RoleBo.ADMIN_ID)
                .eq(OldRole::getDeleteFlag, false)));
    }

    @GetMapping("/page")
    public R<PageData<OldRole>> page(@ModelAttribute OldRole oldRole,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return DatabasePageR.of(oldRoleService.page(new Page<>(pageNum, pageSize), Wrappers.lambdaQuery(oldRole)));
    }

    @GetMapping("/select")
    public R<List<OldRole>> select(@ModelAttribute OldRole oldRole) {
        List<OldRole> list = oldRoleService.list(Wrappers.lambdaQuery(oldRole));
        return R.ok(list);
    }

    @GetMapping("/getById")
    public R<OldRole> getById(@RequestParam("id") Integer id) {
        return R.ok(oldRoleService.getById(id));
    }

    @PostMapping("/updateById")
    public R<Void> updateById(@RequestBody OldRole oldRole) {
        oldRoleService.updateById(oldRole, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

    @PostMapping("/save")
    public R<Void> save(@RequestBody OldRole oldRole) {
        oldRoleService.save(oldRole, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

    @PostMapping("/removeById")
    public R<Void> removeById(@RequestParam("id") Integer id) {
        oldRoleService.removeById(id, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


    @PostMapping("/removeByIds")
    public R<Void> removeByIds(@RequestParam("ids") List<Integer> ids) {
        oldRoleService.removeByIds(ids, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

}
