package com.hyl.minio.util;

import com.hyl.minio.config.MinioProperties;
import io.minio.*;
import io.minio.messages.Bucket;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

/**
 * @Author huyanlong
 * @Date 2021/4/7 21:56
 */
@Component
public class MinioUtils {
    private MinioClient minioClient;

    @Resource
    private MinioProperties minioProperties;


    /**
     * 判断 bucket是否存在
     *
     * @param bucketName: 桶名
     */
    @SneakyThrows
    public boolean bucketExists(String bucketName) {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建 bucket
     *
     * @param bucketName: 桶名
     */
    @SneakyThrows
    public void createBucket(String bucketName) {
        boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!isExist) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 获取全部bucket
     */
    @SneakyThrows
    public List<Bucket> getAllBuckets() {
        return minioClient.listBuckets();
    }

    /**
     * 文件上传
     *
     * @param bucketName: 桶名
     * @param fileName:   文件名
     * @param filePath:   文件路径
     */
    @SneakyThrows
    public void upload(String bucketName, String fileName, String filePath) {
        minioClient.uploadObject(UploadObjectArgs.builder().bucket(bucketName).object(fileName).filename(filePath).contentType("image/png").build());
    }

    /**
     * 文件上传
     *
     * @param bucketName: 桶名
     * @param fileName:   文件名
     * @param stream:     文件流
     */
    @SneakyThrows
    public void upload(String bucketName, String fileName, InputStream stream) {
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(stream, stream.available(), -1).contentType("image/png").build());
    }

    /**
     * 删除文件
     *
     * @param bucketName: 桶名
     * @param fileName:   文件名
     */
    @SneakyThrows
    public void deleteFile(String bucketName, String fileName) {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
    }

    /**
     * 下载文件
     *
     * @param bucketName 桶名
     * @param fileName   文件名
     */
    @SneakyThrows
    public InputStream download(String bucketName, String fileName) {
        return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
    }

    @PostConstruct
    public void init() {
        minioClient = MinioClient.builder().endpoint(minioProperties.getEndpoint()).credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
    }
}
