package com.old.common.upload.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.old.common.upload.config.LocalFileConfig;
import com.old.common.upload.domain.bo.UploadBo;
import com.old.common.upload.event.UploadEvent;
import com.old.common.upload.service.FileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@Service
@ConditionalOnBean(LocalFileConfig.class)
public class LocalFileServerService implements FileService {


    @Autowired
    private LocalFileConfig localFileConfig;


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
        String fileServer = localFileConfig.getFileServer();

        uploadBo.setFileServer(fileServer);
        for (MultipartFile file : files) {
            String fileType = FileUtil.extName(file.getOriginalFilename());
            String fileName = DateUtil.format(LocalDateTime.now(), "yyyy-MM-dd_HH-mm-ss-SSS") + "." + fileType;
            FileUtil.writeFromStream(file.getInputStream(), new File(localFileConfig.getUploadPath() + "/" + fileName));


            uploadBo.setFileName(fileName);
            uploadBo.setFilePath(localFileConfig.getProfile() + fileName);
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
        return localFileConfig.getFileServer();
    }


}
