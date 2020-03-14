package com.itdupan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.itdupan.mapper")
public class ExamSchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamSchoolApplication.class, args);
    }
}
