package com.study.anti_fraud.antifraud_collect_service.controller;

import com.study.anti_fraud.antifraud_collect_service.config.KafkaProducer;
import com.study.anti_fraud.antifraud_common.R.Result;
import com.study.anti_fraud.antifraud_common.R.ResultBuilder;


import com.study.anti_fraud.antifraud_common.constants.KafkaConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class WebSiteController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("collectWebSite")
    public Result collectWebSite(String webSiteUrl) {
       kafkaProducer.send(KafkaConstant.WEBSITE_JSOUP_TOPIC, webSiteUrl);
        return ResultBuilder.success();
    }


    @GetMapping("hello")
    public Result hello() {
        String s = UUID.randomUUID().toString() + new Random().nextInt(999999999) + new Random().nextInt(999999999) + new Random().nextInt(999999999) + new Random().nextInt(999999999) + new Random().nextInt(999999999) + new Random().nextInt(999999999);
        redisTemplate.opsForValue().set(UUID.randomUUID().toString(),s,100, TimeUnit.SECONDS);
       return ResultBuilder.success();
    }
}
