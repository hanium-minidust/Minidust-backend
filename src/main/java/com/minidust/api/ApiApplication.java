package com.minidust.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@EnableScheduling
@SpringBootApplication
public class ApiApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        System.out.println("íėŽėę° : " + new Date());
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}