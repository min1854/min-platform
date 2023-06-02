package com.old.common.upload.service;

import com.old.common.upload.domain.bo.UploadBo;
import com.old.common.upload.event.UploadEvent;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.function.Consumer;

public interface FileService {

    void upload(MultipartFile[] files, Consumer<UploadBo> uploadAfter) throws IOException;

    /**
     * 这个方法会将上传后的数据通过事件发布出去，但很鸡肋的感觉
     *
     * @param files
     * @throws IOException
     * @see UploadEvent 监听这个事件
     */
    void upload(MultipartFile[] files) throws IOException;

    String getFileServer();
}
