package com.old.common.file.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.common.apiAssert.ResultAssertHolder;
import com.old.common.file.domain.OldFile;
import com.old.common.file.domain.req.OldFileViewPageReq;
import com.old.common.file.service.OldFileService;
import com.old.common.mybatis.result.DatabasePageR;
import com.old.common.result.PageData;
import com.old.common.result.R;
import com.old.common.web.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller old_file
 *
 * @author old
 * @date 2023-06-02
 */
@Slf4j
@RestController
@RequestMapping("/oldFile")
public class OldFileController {

    private static final ResultAssertHolder.ResultAssert apiAssert = ResultAssertHolder.apiAssert();

    @Autowired
    private OldFileService oldFileService;


    @GetMapping("/viewPage")
    public R<PageData<OldFile>> viewPage(@ModelAttribute OldFileViewPageReq req,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        log.debug("OldFile 查询参数：{}", req);
        return DatabasePageR.of(oldFileService.lambdaQuery()
                .eq(OldFile::getDeleteFlag, false)
                .page(new Page<>(pageNum, pageSize)));
    }


    @GetMapping("/page")
    public R<PageData<OldFile>> page(@ModelAttribute OldFile oldFile,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return DatabasePageR.of(oldFileService.page(pageNum, pageSize, oldFile));
    }


    @GetMapping("/select")
    public R<List<OldFile>> select(@ModelAttribute OldFile oldFile) {
        return R.ok(oldFileService.list(oldFile));
    }


    @GetMapping("/getById")
    public R<OldFile> getById(@RequestParam("id") Integer id) {
        return R.ok(oldFileService.getById(id));
    }


    @PostMapping("/updateById")
    public R<Void> updateById(@RequestBody OldFile oldFile) {
        oldFileService.updateById(oldFile, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


    @PostMapping("/save")
    public R<Void> save(@RequestBody OldFile oldFile) {
        oldFileService.save(oldFile, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


    @PostMapping("/removeById/{id}")
    public R<Void> removeById(@PathVariable("id") Integer id) {
        oldFileService.removeById(id, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }


    @PostMapping("/removeByIds")
    public R<Void> removeByIds(@RequestBody List<Integer> ids) {
        oldFileService.removeByIds(ids, LoginUtil.loginUserId(), LoginUtil.loginUserName());
        return R.ok();
    }

}
