package com.hyl.minio.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author huyanlong
 * @Date 2021/4/7 21:47
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    /**
     * 是否启用
     */
    private boolean enable;
    /**
     * 桶
     */
    private String bucket;
    /**
     * 地址
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;
}
