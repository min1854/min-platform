package com.old.common.file.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.common.apiAssert.ResultAssertHolder;
import com.old.common.file.domain.OldFileRel;
import com.old.common.file.domain.req.OldFileRelViewPageReq;
import com.old.common.file.service.OldFileRelService;
import com.old.common.mybatis.result.DatabasePageR;
import com.old.common.result.PageData;
import com.old.common.result.R;
import com.old.common.web.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 文件引用表 Controller old_file_rel
 *
 * @author old
 * @date 2023-06-02
 */
@Slf4j
@RestController
@RequestMapping("/oldFileRel")
public class OldFileRelController {

    private static final ResultAssertHolder.ResultAssert apiAssert = ResultAssertHolder.apiAssert();

    @Autowired
    private OldFileRelService oldFileRelService;


    @GetMapping("/viewPage")
    public R<PageData<OldFileRel>> viewPage(@ModelAttribute OldFileRelViewPageReq req,
                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.debug("OldFileRel 查询参数：{}", req);
        return DatabasePageR.of(oldFileRelService.lambdaQuery()
                .eq(OldFileRel::getDeleteFlag, false)
                .page(new Page<>(pageNum, pageSize)));
    }


    @GetMapping("/page")
    public R<PageData<OldFileRel>> page(@ModelAttribute OldFileRel oldFileRel,
                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return DatabasePageR.of(oldFileRelService.page(pageNum, pageSize, oldFileRel));
    }


    @GetMapping("/select")
    public R<List<OldFileRel>> select(@ModelAttribute OldFileRel oldFileRel) {
        return R.ok(oldFileRelService.list(oldFileRel));
    }


    @GetMapping("/getById")
    public R<OldFileRel> getById(@RequestParam("id") Integer id) {
        return R.ok(oldFileRelService.getById(id));
    }


    @PostMapping("/updateById")
    public R<Void> updateById(@RequestBody OldFileRel oldFileRel) {
        oldFileRelService.updateById(oldFileRel, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


    @PostMapping("/save")
    public R<Void> save(@RequestBody OldFileRel oldFileRel) {
        oldFileRelService.save(oldFileRel, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


    @PostMapping("/removeById/{id}")
    public R<Void> removeById(@PathVariable("id") Integer id) {
        oldFileRelService.removeById(id, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


    @PostMapping("/removeByIds")
    public R<Void> removeByIds(@RequestBody List<Integer> ids) {
        oldFileRelService.removeByIds(ids, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

}
