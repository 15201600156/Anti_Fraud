package com.study.anti_fraud.antifraud_collect_service.controller;

import com.study.anti_fraud.antifraud_collect_service.config.KafkaProducer;
import com.study.anti_fraud.antifraud_common.R.Result;
import com.study.anti_fraud.antifraud_common.R.ResultBuilder;


import com.study.anti_fraud.antifraud_common.constants.KafkaConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSiteController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping("collectWebSite")
    public Result collectWebSite(String webSiteUrl) {
       kafkaProducer.send(KafkaConstant.WEBSITE_WEBMAGIC_TOPIC, webSiteUrl);
        return ResultBuilder.success();
    }
}
