package com.study.anti_fraud.antifraud_collect_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class AntifraudCollectServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntifraudCollectServiceApplication.class, args);
    }

}
