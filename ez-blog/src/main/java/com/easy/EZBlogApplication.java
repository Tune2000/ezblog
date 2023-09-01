package com.easy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wxx
 * @create 07--23--22:42
 * @description:
 */
@SpringBootApplication
@MapperScan("com.easy.mapper")
@EnableScheduling
public class EZBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(EZBlogApplication.class,args);
    }
}
