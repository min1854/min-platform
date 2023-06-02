package com.old.business.user.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldPost;
import com.old.business.user.enums.user.OldPostEnums;
import com.old.business.user.service.OldPostService;
import com.old.common.apiAssert.ResultAssertHolder;
import com.old.common.mybatis.result.DatabasePageR;
import com.old.common.mybatis.result.DbPageR;
import com.old.common.result.PageData;
import com.old.common.result.R;
import com.old.common.web.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 岗位信息表 Controller old_post
 *
 * @author old
 * @date 2023-05-20
 */
@Slf4j
@RestController
@RequestMapping("/oldPost")
public class OldPostController {

    private static final ResultAssertHolder.ResultAssert apiAssert = ResultAssertHolder.apiAssert();

    @Autowired
    private OldPostService oldPostService;

    @GetMapping("/viewPage")
    public R<PageData<OldPost>> viewPage(@RequestParam(value = "postCode", required = false) String postCode,
                                         @RequestParam(value = "postName", required = false) String postName,
                                         @RequestParam(value = "postStatus", required = false) OldPostEnums.PostStatus postStatus,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        Page<OldPost> page = oldPostService.page(new Page<>(pageNum, pageSize), oldPostService.lqw()
                .like(StrUtil.isNotBlank(postCode), OldPost::getPostCode, postCode)
                .like(StrUtil.isNotBlank(postName), OldPost::getPostName, postName)
                .eq(postStatus != null, OldPost::getPostStatus, postStatus)
                .eq(OldPost::getDeleteFlag, false)
        );
        return DbPageR.of(page);
    }


    @GetMapping("/page")
    public R<PageData<OldPost>> page(@ModelAttribute OldPost oldPost,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return DatabasePageR.of(oldPostService.page(pageNum, pageSize, oldPost));
    }


    @GetMapping("/select")
    public R<List<OldPost>> select(@ModelAttribute OldPost oldPost) {
        return R.ok(oldPostService.list(oldPost));
    }


    @GetMapping("/getById")
    public R<OldPost> getById(@RequestParam("id") Integer id) {
        return R.ok(oldPostService.getById(id));
    }


    @PostMapping("/updateById")
    public R<Void> updateById(@RequestBody OldPost oldPost) {
        oldPostService.updateById(oldPost, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


    @PostMapping("/save")
    public R<Void> save(@RequestBody OldPost oldPost) {
        oldPostService.save(oldPost, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


    @PostMapping("/removeById/{id}")
    public R<Void> removeById(@PathVariable("id") Integer id) {
        oldPostService.removeById(id, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


    @PostMapping("/removeByIds")
    public R<Void> removeByIds(@RequestBody List<Integer> ids) {
        oldPostService.removeByIds(ids, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

}
