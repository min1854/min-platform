package com.old.common.minio;

import io.minio.MinioClient;

/**
 * 想做什么封装，但是又没有特别好的思路，MinioClient 本身是使用 http 请求封装的方法，想过封装为 feign 进行调用，但又太过花费赶时间，如果以后有
 * 机会再来处理吧。
 */
public class MinioTemplate extends MinioClient {


    protected MinioTemplate(MinioClient client) {
        super(client);
    }
}
