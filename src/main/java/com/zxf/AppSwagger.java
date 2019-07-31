package com.zxf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@SpringBootApplication
@EnableCaching //Cache 中使用Redis
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 20)//设置1天后过期86400   //Spring Session 使用Redis
@EnableSwagger2//开启swagger2
@MapperScan("com.zxf.mapper")//用于无配置文件注解版
public class AppSwagger {

	public static void main(String[] args) {
		SpringApplication.run(AppSwagger.class, args);
	}

}
