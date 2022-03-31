package com.study.anti_fraud.antifaud_reptile_service.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@Slf4j
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaSendResultHandler producerListener;

    public void send(String topic, String message) {
        kafkaTemplate.setProducerListener(producerListener);
        kafkaTemplate.send(new ProducerRecord<>(topic, message));
        log.info("send message end");
    }

}
