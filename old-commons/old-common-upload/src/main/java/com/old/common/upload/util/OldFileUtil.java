package com.old.common.upload.util;


import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.old.common.upload.service.FileService;

public class OldFileUtil {

    private static String fileServer = SpringUtil.getBean(FileService.class).getFileServer();

    public static String fileName() {
        return IdUtil.fastSimpleUUID();
    }

    public static String fileServer() {
        return fileServer;
    }

    /**
     * 参数名可能有问题
     *
     * @param str
     * @return
     */
    public static String completePath(String str) {
        if (str == null) {
            return null;
        }
        return fileServer.concat(str);
    }
}
