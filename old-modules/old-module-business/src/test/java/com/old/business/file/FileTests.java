package com.old.business.file;

import com.old.business.SpringBootTests;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.boot.logging.LoggingSystemProperties.LOG_LEVEL_PATTERN;

public class FileTests extends SpringBootTests {

    @Test
    public void testLog() {
        fileController.testLog("qweq", null);
    }

    /**
     * %d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}} ${LOG_LEVEL_PATTERN:-%5p} ${PID:- } --- [%t] %-40.40logger{39} : %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}
     * %d{$-yyyy-MM-dd HH:mm:ss} [%X{traceId:-},%X{spanId:-}] ${PID:- } --- [%t] %-40.40logger{39} : %m%n-%wEx
     */
    @Test
    public void env() {


        String log_dateformat_pattern = "LOG_DATEFORMAT_PATTERN";
        String log_level_pattern = "LOG_LEVEL_PATTERN";
        String log_exception_conversion_word = "LOG_EXCEPTION_CONVERSION_WORD";
        atLastLog(log_dateformat_pattern, environment.getProperty(log_dateformat_pattern));
        atLastLog(LOG_LEVEL_PATTERN, environment.getProperty(LOG_LEVEL_PATTERN));
        atLastLog(log_exception_conversion_word, environment.getProperty(log_exception_conversion_word));
    }


    @Test
    public void listBuckets() throws InsufficientDataException, ErrorResponseException, IOException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, ServerException {
        List<String> collect = minioClient.listBuckets().stream().map(Bucket::name).collect(Collectors.toList());
        atLastLog("桶名称", collect);
    }

    @Test
    public void putObject() throws IOException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, ServerException {
        File file = new File("D:\\Users\\Pictures\\76beebe14820ddfc460da50c3f45c3c2.jpg");
        try (
                FileInputStream fileInputStream = new FileInputStream(file)
        ) {
            /**
             * 如果对象大小未知，则将 -1 传递给 objectSize 并传递有效的 partSize。如果对象大小已知，则将 -1 传递给 partSize 以进行自动检测；
             * 否则传递有效的 partSize 来控制内存使用和否。上传中的部分。如果 partSize 大于 objectSize，则 objectSize 用作 partSize。
             */
            String bucketName = "test";

            ObjectWriteResponse objectWriteResponse =
                    minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).stream(fileInputStream, file.length(), -1)
                            .object(file.getName()).build());
            atLastLog("空对象", null);
            atLastLog("上传结果", objectWriteResponse);
            atLastLog("上传结果 文件名称", objectWriteResponse.object());
            atLastLog("上传结果 版本id", objectWriteResponse.versionId());
            atLastLog("上传结果 etag", objectWriteResponse.etag());
            atLastLog("上传结果 桶", objectWriteResponse.bucket());
            atLastLog("上传结果 region", objectWriteResponse.region());
            atLastLog("上传结果 请求头", objectWriteResponse.headers());
        }
    }
}
