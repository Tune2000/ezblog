package com.easy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wxx
 * @create 07--23--22:42
 * @description:
 */
@SpringBootApplication
@MapperScan("com.easy.mapper")
public class EZBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(EZBlogApplication.class,args);
    }
}
