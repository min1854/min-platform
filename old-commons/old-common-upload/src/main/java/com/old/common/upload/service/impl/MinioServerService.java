package com.old.common.upload.service.impl;

import cn.hutool.core.io.FileUtil;
import com.old.common.enums.ResultEnum;
import com.old.common.exception.ResultException;
import com.old.common.minio.config.MinioProperties;
import com.old.common.upload.domain.bo.UploadBo;
import com.old.common.upload.event.UploadEvent;
import com.old.common.upload.service.FileService;
import com.old.common.upload.util.OldFileUtil;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@ConditionalOnProperty(value = "old.file.serverType", havingValue = "minio")
public class MinioServerService implements FileService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioProperties minioProperties;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void upload(MultipartFile[] files, Consumer<UploadBo> upload) throws IOException {
        /**
         * 只创建一个对象，可以避免重复的创建，但是问题在于，如果调用者直接保存 bo，那么包含的就只是同一个对象了
         * 把对象的创建交给外部吗？
         */
        UploadBo uploadBo = new UploadBo();
        uploadBo.setFileServer(minioProperties.getServer());
        try {
            for (MultipartFile file : files) {
                String fileType = FileUtil.extName(file.getOriginalFilename());
                ObjectWriteResponse response = minioClient.putObject(PutObjectArgs.builder().bucket(minioProperties.getBucket())
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .object(OldFileUtil.fileName() + "." + fileType)
                        .build());
                uploadBo.setFileName(response.object());
                uploadBo.setFilePath("/" + response.bucket() + "/" + response.object());
                uploadBo.setAllPath(minioProperties.getServer() + uploadBo.getFilePath());
                uploadBo.setOriginalFilename(file.getOriginalFilename());
                uploadBo.setFileType(fileType);
                upload.accept(uploadBo);
            }
        } catch (MinioException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new ResultException(ResultEnum.FILE_UPLOAD_FAIL, e);
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
        return minioProperties.getServer();
    }


}
