package com.yujian.collect;

import com.yujian.clients.ProductClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan(basePackages = "com.yujian.collect.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {ProductClient.class})
public class CollectApplication {
    public static void main(String[] args) {
        SpringApplication.run(CollectApplication.class);
    }
}
