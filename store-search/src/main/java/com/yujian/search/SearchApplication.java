package com.yujian.search;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.yujian.clients.ProductClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

//排除自动导入数据库配置 , 否则会出现配置连接池信息异常
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class,
        HibernateJpaAutoConfiguration.class
})
@EnableFeignClients(clients = {ProductClient.class})
public class SearchApplication {
    public static void main(String[] args) {
        System.out.println("SearchApplication.main -----");
        SpringApplication.run(SearchApplication.class, args);
        System.out.println("SearchApplication.main +++++");
    }
}
