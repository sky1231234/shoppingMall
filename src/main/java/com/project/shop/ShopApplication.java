package com.project.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class ShopApplication {
//	(exclude={DataSourceAutoConfiguration.class})
//	scanBasePackages = "com.project.shop.member.repository.PointRepository"

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}
}
