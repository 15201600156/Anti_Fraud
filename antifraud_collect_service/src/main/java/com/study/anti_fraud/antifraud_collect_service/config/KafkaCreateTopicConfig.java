package com.study.anti_fraud.antifraud_collect_service.config;

import com.study.anti_fraud.antifraud_common.constants.KafkaConstant;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;


@Configuration
@EnableKafka
public class KafkaCreateTopicConfig {

    @Bean
    public NewTopic initialTopic() {
        //第一个是参数是topic名字，第二个参数是分区个数，第三个是topic的复制因子个数
        //当broker个数为1个时会创建topic失败，
        //提示：replication factor: 2 larger than available brokers: 1
        //只有在集群中才能使用kafka的备份功能
        return new NewTopic(KafkaConstant.WEBSITE_WEBMAGIC_TOPIC, 10, (short) 1);
    }

}
