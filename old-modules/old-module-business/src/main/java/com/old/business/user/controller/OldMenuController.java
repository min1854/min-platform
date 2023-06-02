package com.old.business.user.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldMenu;
import com.old.business.user.domain.OldRoleMenuRel;
import com.old.business.user.domain.resp.RoleMenuTreeSelectResp;
import com.old.business.user.enums.user.OldMenuEnums;
import com.old.business.user.service.OldMenuService;
import com.old.business.user.service.OldRoleMenuRelService;
import com.old.business.user.service.OldRoleService;
import com.old.business.user.util.MenuUtil;
import com.old.common.apiAssert.ResultAssertHolder;
import com.old.common.domain.login.LoginUser;
import com.old.common.domain.login.RouterBo;
import com.old.common.domain.login.html.HtmlMenuBo;
import com.old.common.enums.ResultEnum;
import com.old.common.mybatis.result.DatabasePageR;
import com.old.common.result.PageData;
import com.old.common.result.R;
import com.old.common.web.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 菜单权限表 Controller old_menu
 *
 * @author old
 * @date 2023-04-02
 */
@Slf4j
@RestController
@RequestMapping("/oldMenu")
public class OldMenuController {

    private static final ResultAssertHolder.ResultAssert apiAssert = ResultAssertHolder.apiAssert();

    @Autowired
    private OldMenuService oldMenuService;

    @Autowired
    private OldRoleService oldRoleService;

    @Autowired
    private OldRoleMenuRelService oldRoleMenuRelService;

    @GetMapping("/treeSelect")
    public R<List<HtmlMenuBo>> treeSelect() {

        List<OldMenu> oldMenus = oldMenuService.selectMenuListByUserId(LoginUtil.loginUserId(), null,
                null, null, LoginUser.DEFAULT_USER_ID);
        var htmlMenuTree = MenuUtil.buildMenuTree(oldMenus.stream().map(oldMenu -> {
            HtmlMenuBo bo = new HtmlMenuBo();
            bo.setId(oldMenu.getId().intValue());
            BeanUtils.copyProperties(oldMenu, bo);
            return bo;
        }).toList());

        return R.ok(htmlMenuTree);
    }

    @PostMapping("/removeMenu/{id}")
    public R<Void> removeMenu(@PathVariable("id") Long id) {
        apiAssert.nonNull(oldMenuService.lambdaQuery()
                        .eq(OldMenu::getParentId, id)
                        .eq(OldMenu::getDeleteFlag, false)
                        .last("limit 1")
                        .one(), ResultEnum.CHILD_MENU_EXISTS_NOT_DELETE)
                .nonNull(oldRoleMenuRelService.lambdaQuery()
                        .eq(OldRoleMenuRel::getMenuId, id)
                        .last("limit 1")
                        .one(), ResultEnum.MENU_ALLOCATED_CANT_DELETE);
        this.oldMenuService.removeById(id, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

    @GetMapping("/roleMenuTreeSelect/{roleId}")
    public R<RoleMenuTreeSelectResp> roleMenuTreeSelect(@PathVariable("roleId") Integer roleId) {

        List<OldMenu> oldMenus = oldMenuService.selectMenuListByUserId(LoginUtil.loginUserId(), null,
                null, null, LoginUser.DEFAULT_USER_ID);
        var htmlMenuTree = MenuUtil.buildMenuTree(oldMenus.stream().map(oldMenu -> {
            HtmlMenuBo bo = new HtmlMenuBo();
            bo.setId(oldMenu.getId().intValue());
            BeanUtils.copyProperties(oldMenu, bo);
            return bo;
        }).toList());

        return R.ok(new RoleMenuTreeSelectResp(oldRoleService.selectMenuListByRoleId(roleId), htmlMenuTree));
    }


    @GetMapping("/selectMenuListByUserId")
    public R<List<OldMenu>> selectMenuListByUserId(@RequestParam(value = "menuName", required = false) String menuName,
                                                   @RequestParam(value = "menuVisible", required = false) OldMenuEnums.MenuVisibleEnum menuVisible,
                                                   @RequestParam(value = "menuStatus", required = false) OldMenuEnums.MenuStatusEnum menuStatus) {
        return R.ok(oldMenuService.selectMenuListByUserId(LoginUtil.loginUserId(), menuName, menuVisible, menuStatus, LoginUser.DEFAULT_USER_ID));
    }

    @GetMapping("/getRouters")
    public R<List<RouterBo>> getRouters() {
        return R.ok(LoginUtil.getLoginUser().getRouters());
    }

    @GetMapping("/page")
    public R<PageData<OldMenu>> page(@ModelAttribute OldMenu oldMenu,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return DatabasePageR.of(oldMenuService.page(new Page<>(pageNum, pageSize), Wrappers.lambdaQuery(oldMenu)));
    }

    @GetMapping("/select")
    public R<List<OldMenu>> select(@ModelAttribute OldMenu oldMenu) {
        List<OldMenu> list = oldMenuService.list(Wrappers.lambdaQuery(oldMenu));
        return R.ok(list);
    }

    @GetMapping("/getById")
    public R<OldMenu> getById(@RequestParam("id") Long id) {
        return R.ok(oldMenuService.getById(id));
    }

    @PostMapping("/updateById")
    public R<Void> updateById(@RequestBody OldMenu oldMenu) {
        oldMenuService.updateById(oldMenu, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

    @PostMapping("/save")
    public R<Void> save(@RequestBody OldMenu oldMenu) {
        oldMenuService.save(oldMenu, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

    @PostMapping("/removeById")
    public R<Void> removeById(@RequestParam("id") Long id) {
        oldMenuService.removeById(id, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


    @PostMapping("/removeByIds")
    public R<Void> removeByIds(@RequestParam("ids") List<Long> ids) {
        oldMenuService.removeByIds(ids, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

}
