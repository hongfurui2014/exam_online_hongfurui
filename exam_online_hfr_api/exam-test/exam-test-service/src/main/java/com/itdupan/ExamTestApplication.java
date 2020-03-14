package com.itdupan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ExamTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamTestApplication.class, args);
    }
}
