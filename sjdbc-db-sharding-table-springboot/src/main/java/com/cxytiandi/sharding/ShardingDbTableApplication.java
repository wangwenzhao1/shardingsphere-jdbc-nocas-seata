package com.cxytiandi.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;

/**
 * Spring Boot版 Sharding JDBC 分库分表案例
 *
 * @author yinjihuan
 *
 * @about http://cxytiandi.com/about
 */
@EnableDiscoveryClient
@EnableFeignClients
//@SpringBootApplication
//@ComponentScan(basePackages = {"com.cxytiandi.sharding.repository"})
@MapperScan("com.cxytiandi.sharding.repository")
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class ShardingDbTableApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShardingDbTableApplication.class, args);
	}
}
