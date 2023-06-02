package com.old.business.user.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldMenuPath;
import com.old.business.user.service.OldMenuPathService;
import com.old.common.mybatis.result.DatabasePageR;
import com.old.common.result.PageData;
import com.old.common.result.R;
import com.old.common.web.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 菜单层级关系 Controller old_menu_path
 *
 * @author old
 * @date 2022-10-17
 */
@Slf4j
@RestController
@RequestMapping("/oldMenuPath")
public class OldMenuPathController {
    @Autowired
    OldMenuPathService oldMenuPathService;

    @GetMapping("/page")
    public R<PageData<OldMenuPath>> page(@ModelAttribute OldMenuPath oldMenuPath,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return DatabasePageR.of(oldMenuPathService.page(new Page<>(pageNum, pageSize), Wrappers.lambdaQuery(oldMenuPath)));
    }


    @GetMapping("/select")
    public R<List<OldMenuPath>> select(@ModelAttribute OldMenuPath oldMenuPath) {
        List<OldMenuPath> list = oldMenuPathService.list(Wrappers.lambdaQuery(oldMenuPath));
        return R.ok(list);
    }

    @GetMapping("/getById")
    public R<OldMenuPath> getById(@RequestParam("id") Integer id) {
        return R.ok(oldMenuPathService.getById(id));
    }

    @PostMapping("/updateById")
    public R<?> updateById(@RequestBody OldMenuPath oldMenuPath) {
        oldMenuPathService.updateByKey(oldMenuPath, LoginUtil.loginUserName(), LoginUtil.loginUserId());
        return R.ok();
    }

    @PostMapping("/save")
    public R<?> save(@RequestBody OldMenuPath oldMenuPath) {
        oldMenuPathService.insert(oldMenuPath, LoginUtil.loginUserName(), LoginUtil.loginUserId());
        return R.ok();
    }


    @PostMapping("/removeById")
    public R<?> removeById(@RequestParam("id") Integer id) {
        oldMenuPathService.removeById(id, LoginUtil.loginUserName(), LoginUtil.loginUserId());
        return R.ok();
    }


}
