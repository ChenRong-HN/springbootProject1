package com.yc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.yc.dao")
@EnableSwagger2  // 启用这个框架专有注解解析器
@EnableRedisHttpSession

public class ResApp {
    public static void main(String[] args) {
        SpringApplication.run(ResApp.class,args);
    }
}
