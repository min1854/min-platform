package com.old.common.file.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.old.common.apiAssert.ResultAssertHolder;
import com.old.common.enums.ResultEnum;
import com.old.common.file.domain.OldUploadFile;
import com.old.common.file.service.OldFileService;
import com.old.common.file.service.OldUploadFileService;
import com.old.common.mybatis.result.DatabasePageR;
import com.old.common.result.PageData;
import com.old.common.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


/**
 * 文件 Controller old_upload_file
 *
 * @author old
 * @date 2023-05-26
 */
@Slf4j
@RestController
@RequestMapping("/oldUploadFile")
public class OldUploadFileController {

    private static final ResultAssertHolder.ResultAssert apiAssert = ResultAssertHolder.apiAssert();

    @Autowired
    private OldUploadFileService oldUploadFileService;

    @Autowired
    private OldFileService oldFileService;

    /**
     * HTTP/1.1 200 OK
     * Vary: Origin
     * Vary: Access-Control-Request-Method
     * Vary: Access-Control-Request-Headers
     * Content-Type: application/octet-stream
     * Content-Length: 696
     * Date: Fri, 26 May 2023 06:38:59 GMT
     * Keep-Alive: timeout=60
     * Connection: keep-alive
     *
     * @param id
     * @return
     * @throws IOException
     */
    // @GetMapping(value = "/pre/{id}"/*, produces = {org.springframework.http.MediaType.IMAGE_PNG_VALUE,
    // org.springframework.http.MediaType.IMAGE_PNG_VALUE, org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE}*/)
    // @GetMapping(value = "/pre/{id}"/*, produces = org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE*/)
    @GetMapping(value = "/file/{id}")
    public byte[] getFile(@PathVariable("id") Integer id
                          // ,jakarta.servlet.http.HttpServletResponse response
    ) throws IOException {
        OldUploadFile uploadFile = oldUploadFileService.getById(id);
        apiAssert.isNull(uploadFile, ResultEnum.FILE_NOT_EXISTS);
        // response.getOutputStream().write(uploadFile.getDbFile());
        return uploadFile.getDbFile();
    }


    @GetMapping("/page")
    public R<PageData<OldUploadFile>> page(@ModelAttribute OldUploadFile oldUploadFile,
                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return DatabasePageR.of(oldUploadFileService.lambdaQuery(oldUploadFile).page(new Page<>(pageNum, pageSize)));
    }


    @GetMapping("/select")
    public R<List<OldUploadFile>> select(@ModelAttribute OldUploadFile oldUploadFile) {
        return R.ok(oldUploadFileService.lambdaQuery(oldUploadFile).list());
    }


    @GetMapping("/getById")
    public R<OldUploadFile> getById(@RequestParam("id") Integer id) {
        return R.ok(oldUploadFileService.getById(id));
    }


    @PostMapping("/updateById")
    public R<Void> updateById(@RequestBody OldUploadFile oldUploadFile) {
        oldUploadFileService.updateById(oldUploadFile);
        return R.ok();
    }


    @PostMapping("/save")
    public R<Void> save(@RequestBody OldUploadFile oldUploadFile) {
        oldUploadFileService.save(oldUploadFile);
        return R.ok();
    }


    @PostMapping("/removeById/{id}")
    public R<Void> removeById(@PathVariable("id") Integer id) {
        oldUploadFileService.removeById(id);
        return R.ok();
    }


    @PostMapping("/removeByIds")
    public R<Void> removeByIds(@RequestBody List<Integer> ids) {
        oldUploadFileService.removeByIds(ids);
        return R.ok();
    }

}
