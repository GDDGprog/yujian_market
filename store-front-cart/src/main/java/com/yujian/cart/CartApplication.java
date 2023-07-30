package com.yujian.cart;

import com.yujian.clients.ProductClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan(basePackages = "com.yujian.cart.mapper")
@SpringBootApplication
@EnableFeignClients(clients = {ProductClient.class})
public class CartApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }
}
