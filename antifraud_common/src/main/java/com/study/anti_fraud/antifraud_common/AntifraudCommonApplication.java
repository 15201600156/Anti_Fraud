package com.study.anti_fraud.antifraud_common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class AntifraudCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntifraudCommonApplication.class, args);
    }

}
