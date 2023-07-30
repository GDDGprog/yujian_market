package com.yujian.carousel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.yujian.carousel.mapper")
@SpringBootApplication
@EnableCaching
public class CarouselApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarouselApplication.class,args);
    }
}
