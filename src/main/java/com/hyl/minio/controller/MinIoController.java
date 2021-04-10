package com.hyl.minio.controller;

import com.hyl.minio.util.MinioUtils;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @Author huyanlong
 * @Date 2021/4/7 21:34
 */
@RestController
public class MinIoController {

    @Autowired
    MinioUtils inioUtils;

    @Value("${minio.bucket}")
    String bucket;

    @PostMapping("/upload")
    public String upload(@RequestParam("data") MultipartFile data, @RequestParam(name = "bucketName", required = false) String bucketName) throws Exception{
        if (!StringUtils.hasLength(bucketName)) {
            bucketName = bucket;
        }
        String fileName = data.getOriginalFilename();
        InputStream inputStram = data.getInputStream();
        inioUtils.upload(bucketName, fileName, inputStram);
        return "上传成功";
    }

    /**
     * 测试数据
     * @return
     */
    @GetMapping("hello")
    public String getHello() {
        return "hello";
    }
}
