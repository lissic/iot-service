package com.hlytec.cloud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;

import lombok.Data;

/**
 * @description: InfluxDbConfig
 * @author: zero
 * @date: 2021/7/6 17:18
 */
@Data
@ConfigurationProperties(prefix = "spring.influx")
@Component
public class InfluxDbConfig {
    private String username;
    private String password;
    private String url;
    private String database;
    private String retentionPolicy;

    @Bean
    public InfluxDBClient init() {
        return InfluxDBClientFactory.createV1(url,username,password.toCharArray(),database,retentionPolicy);
    }
}
