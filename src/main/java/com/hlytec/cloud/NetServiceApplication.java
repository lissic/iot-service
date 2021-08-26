package com.hlytec.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: NetServiceApplication
 * @author: zero
 * @date: 2021/4/29 16:30
 */

@SpringBootApplication(scanBasePackages = "com.hlytec.cloud")
public class NetServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetServiceApplication.class, args);
    }
}
