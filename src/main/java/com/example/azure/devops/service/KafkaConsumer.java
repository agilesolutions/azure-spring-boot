package com.example.azure.devops.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Slf4j
@Getter
@Setter
@Component
public class KafkaConsumer {

    private CountDownLatch latch = new CountDownLatch(1);
    private String payload = null;

    @KafkaListener(topics = "${test.topic}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        log.info("received payload='{}'", consumerRecord.toString());
        setPayload(consumerRecord.toString());
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }


}