package com.dbproj.hms.service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.dbproj.hms.booktimeslotservice")

public class BookTimeslotServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookTimeslotServiceApplication.class, args);
    }
}
}
