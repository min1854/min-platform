package com.old.common.upload.domain.resp;


import lombok.Data;

import java.io.Serializable;

@Data
public class UploadResp implements Serializable {
    private String fileName;
    private String fileServer;
    private String filePath;
    private Integer fileId;
    private String allPath;
}

