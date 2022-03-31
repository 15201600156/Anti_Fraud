package com.study.anti_fraud.antifaud_reptile_service.kafka;


import com.study.anti_fraud.antifaud_reptile_service.config.ThreadPoolConfig;
import com.study.anti_fraud.antifaud_reptile_service.thread.WebSiteHtmlUnitThread;
import com.study.anti_fraud.antifaud_reptile_service.thread.WebSiteJsoupThread;
import com.study.anti_fraud.antifaud_reptile_service.thread.WebSiteWebmagicThread;
import com.study.anti_fraud.antifraud_common.untils.WebSiteUtil;
import com.study.anti_fraud.antifraud_common.constants.KafkaConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class WebSiteConsumer {


    @Autowired
    ThreadPoolConfig threadPoolConfig;

    @KafkaListener(topics = KafkaConstant.WEBSITE_JSOUP_TOPIC, groupId = "website-consumer-group")
    public void receiveJsoup(String webSiteUrl) {
        if (Objects.nonNull(webSiteUrl)) {
            log.info("接收到kafka消息系统的网站地址:" + webSiteUrl);
            threadPoolConfig.addExecuteTask(new WebSiteJsoupThread(WebSiteUtil.getWebSiteUrl(webSiteUrl)));
        }
    }

    @KafkaListener(topics = KafkaConstant.WEBSITE_HTMLUNIT_TOPIC, groupId = "website-consumer-group")
    public void receiveHtmlUnit(String webSiteUrl) {
        if (Objects.nonNull(webSiteUrl)) {
            log.info("接收到kafka消息系统的网站地址:" + webSiteUrl);
            threadPoolConfig.addExecuteTask(new WebSiteHtmlUnitThread(WebSiteUtil.getWebSiteUrl(webSiteUrl)));
        }
    }

    @KafkaListener(topics = KafkaConstant.WEBSITE_WEBMAGIC_TOPIC, groupId = "website-consumer-group")
    public void receiveWebmagic(String webSiteUrl) {
        if (Objects.nonNull(webSiteUrl)) {
            log.info("接收到kafka消息系统的网站地址:" + webSiteUrl);
            threadPoolConfig.addExecuteTask(new WebSiteWebmagicThread(WebSiteUtil.getWebSiteUrl(webSiteUrl)));
        }
    }
}
