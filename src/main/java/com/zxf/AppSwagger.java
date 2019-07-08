package com.zxf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@SpringBootApplication
@EnableSwagger2//开启swagger2
@MapperScan("com.zxf.mapper")//用于无配置文件注解版
public class AppSwagger {

	public static void main(String[] args) {
		SpringApplication.run(AppSwagger.class, args);
	}

}
