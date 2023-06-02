package com.old.business.file;

import cn.hutool.core.io.FileUtil;
import com.old.business.SpringBootTests;
import com.old.common.file.domain.OldUploadFile;
import org.junit.jupiter.api.Test;

public class DataBaseFileTests extends SpringBootTests {

    @Test
    public void upload() {
        OldUploadFile file = new OldUploadFile();
        file.setDbFile(FileUtil.readBytes("D:\\java\\Demo\\gitee\\old-platform\\temp\\stty.exe.stackdump"));
        file.setFileType("text");
        oldUploadFileController.save(file);
    }

}
