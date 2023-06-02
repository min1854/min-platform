package com.old.common.upload.service.impl;

import cn.hutool.core.io.FileUtil;
import com.old.common.apiAssert.ResultAssertHolder;
import com.old.common.enums.ResultEnum;
import com.old.common.file.domain.OldUploadFile;
import com.old.common.file.service.OldUploadFileService;
import com.old.common.upload.config.DataBaseFileConfig;
import com.old.common.upload.domain.bo.UploadBo;
import com.old.common.upload.event.UploadEvent;
import com.old.common.upload.service.FileService;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@ConditionalOnBean(DataBaseFileConfig.class)
public class DataBaseFileServerService implements FileService {

    public ResultAssertHolder.ResultAssert apiAssert = ResultAssertHolder.apiAssert();

    @Autowired
    private DataBaseFileConfig dataBaseFileConfig;

    @Autowired
    private OldUploadFileService oldUploadFileService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void upload(MultipartFile[] files, Consumer<UploadBo> upload) throws IOException {
        UploadBo uploadBo = new UploadBo();
        /**
         *   StringBuffer url = request.getRequestURL();
         * String contextPath = request.getServletContext().getContextPath();
         * return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
         * */
        String fileServer = dataBaseFileConfig.getFileServer();
        uploadBo.setFileServer(fileServer);
        for (MultipartFile file : files) {
            String fileType = FileUtil.extName(file.getOriginalFilename());
            OldUploadFile oldUploadFile = new OldUploadFile();
            oldUploadFile.setFileType(fileType);
            oldUploadFile.setDbFile(IOUtils.toByteArray(file.getInputStream()));
            apiAssert.isFalse(oldUploadFileService.save(oldUploadFile), ResultEnum.FILE_UPLOAD_FAIL);
            uploadBo.setFileName(oldUploadFile.getId().toString());
            uploadBo.setFilePath(dataBaseFileConfig.getProfile() + oldUploadFile.getId());
            uploadBo.setAllPath(fileServer + uploadBo.getFilePath());
            uploadBo.setOriginalFilename(file.getOriginalFilename());
            uploadBo.setFileType(fileType);
            upload.accept(uploadBo);
        }
    }


    @Override
    public void upload(MultipartFile[] files) throws IOException {
        List<UploadBo> bos = new ArrayList<>(files.length);
        upload(files, bo -> {
            UploadBo uploadBo = new UploadBo();
            BeanUtils.copyProperties(bo, uploadBo);
            bos.add(uploadBo);
        });
        applicationEventPublisher.publishEvent(new UploadEvent(bos));

    }

    @Override
    public String getFileServer() {
        return dataBaseFileConfig.getFileServer();
    }


}
