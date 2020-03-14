package com.itdupan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ExamEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamEurekaApplication.class, args);
    }
}
