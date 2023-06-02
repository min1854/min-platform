package com.old.common.upload.controller;

import com.old.common.apiAssert.ResultAssertHolder;
import com.old.common.base.BaseException;
import com.old.common.enums.ResultEnum;
import com.old.common.file.domain.OldFile;
import com.old.common.file.service.OldFileService;
import com.old.common.result.R;
import com.old.common.upload.domain.bo.UploadBo;
import com.old.common.upload.domain.resp.UploadResp;
import com.old.common.upload.service.FileService;
import com.old.common.web.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    OldFileService oldFileService;

    @GetMapping("/testLog")
    public R<?> testLog(@ModelAttribute("message") String message, @Value("${LOG_LEVEL_PATTERN}") String loglevelPattern) {
        log.debug("debug：%{spanId}%{traceId}，{} {}", loglevelPattern, message);
        log.info("info：%{spanId}%{traceId}，{} {}", loglevelPattern, message);
        log.warn("warn：%{spanId}%{traceId}，{} {}", loglevelPattern, message);
        log.error("error：%{spanId}%{traceId}，{} {}", loglevelPattern, message, new RuntimeException());
        return R.ok();
    }

    @GetMapping("/throwException")
    public void throwException() {
        throw new BaseException("抛出异常");
    }


    @PostMapping("/echoUpload")
    public R<?> echoUpload(@RequestPart("files") MultipartFile[] files) throws IOException {
        if (files == null) {
            log.error("上传文件为空");
            return R.fail();
        }
        for (MultipartFile file : files) {
            //  contentType：multipart/form-data
            log.debug("contentType：{}", file.getContentType());
            //  size：0
            log.debug("size：{}", file.getSize());
            //  toString: org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@423a5f09
            log.debug("toString: {}", file);
        }
        return R.ok();
    }

    /**
     * @param files
     */
    @PostMapping("/upload")
    public R<List<UploadResp>> upload(@RequestPart(value = "files") MultipartFile[] files) throws IOException {
        var userId = LoginUtil.loginUserId();
        var username = LoginUtil.loginUserName();
        ResultAssertHolder.ResultAssert apiAssert = ResultAssertHolder.apiAssert();
        apiAssert.isEmpty(files, ResultEnum.EMPTY_FILE);
        log.debug("文件上传参数，文件数量：{}，用户 id：{}，用户名：{}", files.length, userId, username);


        List<OldFile> fileList = new ArrayList<>(files.length);
        fileService.upload(files, (uploadBo) -> {
            fileList.add(toFile(uploadBo, username, userId));
        });
        apiAssert.isFalse(oldFileService.saveBatch(fileList), ResultEnum.FILE_UPLOAD_FAIL);

        return R.ok(toResp(fileList));
    }


    private List<UploadResp> toResp(List<OldFile> fileList) {
        return fileList.stream().map(oldFile -> {
            UploadResp uploadResp = new UploadResp();
            uploadResp.setFileName(oldFile.getFileName());
            uploadResp.setFileId(oldFile.getId());
            uploadResp.setFileServer(oldFile.getFileServer());
            uploadResp.setFilePath(oldFile.getFilePath());
            uploadResp.setAllPath(uploadResp.getFileServer() + oldFile.getFilePath());
            return uploadResp;
        }).collect(Collectors.toList());
    }


    private OldFile toFile(UploadBo uploadBo, String username, Integer userId) {
        OldFile oldFile = new OldFile();
        oldFile.setOriginalFileName(uploadBo.getOriginalFilename());
        oldFile.setFileType(uploadBo.getFileType());
        oldFile.setFileName(uploadBo.getFileName());
        oldFile.setFileServer(uploadBo.getFileServer());
        oldFile.setFilePath(uploadBo.getFilePath());
        oldFile.setCreateUser(username);
        oldFile.setCreateUserId(userId);
        return oldFile;
    }
}

