package com.old.common.upload.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadBo implements Serializable {

    private String fileName;
    private String filePath;
    private String fileServer;
    private String allPath;
    private String originalFilename;
    private String fileType;
}
