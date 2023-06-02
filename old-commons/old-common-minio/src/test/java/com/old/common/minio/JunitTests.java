package com.old.common.minio;

import com.old.common.base.BaseTest;
import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 这个类就是不能运行，不知道为什么
 */
public class JunitTests extends BaseTest {

    MinioClient minioClient;
    private String url = "old.com:9000";
    private String accessKey = "rootUser";
    private String secretKey = "rootPassword";

    @BeforeAll
    public void init() {

        minioClient = MinioClient.builder().credentials(accessKey, secretKey)
                .endpoint(url)
                .build();
    }


    @Test
    public void listBuckets() throws InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, ServerException {
        List<Bucket> buckets = minioClient.listBuckets();
        buckets.stream().map(Bucket::name).forEach(System.out::println);
    }

    @Test
    public void upload() throws InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, ServerException {
        minioClient.putObject(null);
    }
}
