package com.old.business.user.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldUserPost;
import com.old.business.user.service.OldUserPostService;
import com.old.common.mybatis.result.DatabasePageR;
import com.old.common.result.PageData;
import com.old.common.result.R;
import com.old.common.web.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 用户与岗位关联表 Controller old_user_post
 *
 * @author old
 * @date 2023-04-13
 */
@Slf4j
@RestController
@RequestMapping("/oldUserPost")
public class OldUserPostController {
    @Autowired
    OldUserPostService oldUserPostService;

    @GetMapping("/page")
    public R<PageData<OldUserPost>> page(@ModelAttribute OldUserPost oldUserPost,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return DatabasePageR.of(oldUserPostService.page(new Page<>(pageNum, pageSize), Wrappers.lambdaQuery(oldUserPost)));
    }

    @GetMapping("/select")
    public R<List<OldUserPost>> select(@ModelAttribute OldUserPost oldUserPost) {
        List<OldUserPost> list = oldUserPostService.list(Wrappers.lambdaQuery(oldUserPost));
        return R.ok(list);
    }

    @GetMapping("/getById")
    public R<OldUserPost> getById(@RequestParam("id") Integer id) {
        return R.ok(oldUserPostService.getById(id));
    }

    @PostMapping("/updateById")
    public R<Void> updateById(@RequestBody OldUserPost oldUserPost) {
        oldUserPostService.updateById(oldUserPost, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

    @PostMapping("/save")
    public R<Void> save(@RequestBody OldUserPost oldUserPost) {
        oldUserPostService.save(oldUserPost, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

    @PostMapping("/removeById")
    public R<Void> removeById(@RequestParam("id") Integer id) {
        oldUserPostService.removeById(id, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


    @PostMapping("/removeByIds")
    public R<Void> removeByIds(@RequestParam("ids") List<Integer> ids) {
        oldUserPostService.removeByIds(ids, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

}
