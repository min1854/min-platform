package com.old.business.user.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.business.user.domain.OldNotice;
import com.old.business.user.enums.user.OldNoticeEnums;
import com.old.business.user.service.OldNoticeService;
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
 * 通知公告表 Controller old_notice
 *
 * @author old
 * @date 2023-05-20
 */
@Slf4j
@RestController
@RequestMapping("/oldNotice")
public class OldNoticeController {

    private static final ResultAssertHolder.ResultAssert apiAssert = ResultAssertHolder.apiAssert();

    @Autowired
    private OldNoticeService oldNoticeService;

    @GetMapping("/viewPage")
    public R<PageData<OldNotice>> viewPage(@RequestParam(value = "noticeTitle", required = false) String noticeTitle,
                                           @RequestParam(value = "createUser", required = false) String createUser,
                                           @RequestParam(value = "noticeType", required = false) OldNoticeEnums.NoticeType noticeType,
                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        Page<OldNotice> page = oldNoticeService.page(new Page<>(pageNum, pageSize), oldNoticeService.lqw()
                .like(StrUtil.isNotBlank(noticeTitle), OldNotice::getNoticeTitle, noticeTitle)
                .like(StrUtil.isNotBlank(createUser), OldNotice::getCreateUser, createUser)
                .eq(noticeType != null, OldNotice::getNoticeType, noticeType)
                .eq(OldNotice::getDeleteFlag, false)
        );
        return DbPageR.of(page);
    }

    @GetMapping("/page")
    public R<PageData<OldNotice>> page(@ModelAttribute OldNotice oldNotice,
                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return DatabasePageR.of(oldNoticeService.page(pageNum, pageSize, oldNotice));
    }


    @GetMapping("/select")
    public R<List<OldNotice>> select(@ModelAttribute OldNotice oldNotice) {
        return R.ok(oldNoticeService.list(oldNotice));
    }


    @GetMapping("/getById")
    public R<OldNotice> getById(@RequestParam("id") Integer id) {
        return R.ok(oldNoticeService.getById(id));
    }


    @PostMapping("/updateById")
    public R<Void> updateById(@RequestBody OldNotice oldNotice) {
        oldNoticeService.updateById(oldNotice, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


    @PostMapping("/save")
    public R<Void> save(@RequestBody OldNotice oldNotice) {
        oldNoticeService.save(oldNotice, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


    @PostMapping("/removeById/{id}")
    public R<Void> removeById(@PathVariable("id") Integer id) {
        oldNoticeService.removeById(id, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


    @PostMapping("/removeByIds")
    public R<Void> removeByIds(@RequestBody List<Integer> ids) {
        oldNoticeService.removeByIds(ids, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

}
