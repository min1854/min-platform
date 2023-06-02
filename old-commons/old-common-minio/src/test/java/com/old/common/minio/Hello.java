package com.old.common.minio;

import io.minio.MinioClient;
import io.minio.errors.*;
import io.minio.messages.Bucket;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Hello {


    static MinioClient minioClient;
    private static String url = "http://old.com:9000";
    private static String accessKey = "rootUser";
    private static String secretKey = "rootPassword";

    public static void main(String[] args) throws InsufficientDataException, ErrorResponseException, IOException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, ServerException {
        System.out.println("你好");

        minioClient = MinioClient.builder().credentials(accessKey, secretKey)
                .endpoint(url)
                .build();


        listBuckets();
    }


    public static void listBuckets() throws InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, ServerException {
        List<Bucket> buckets = minioClient.listBuckets();
        buckets.stream().map(Bucket::name).forEach(System.out::println);
    }
}
