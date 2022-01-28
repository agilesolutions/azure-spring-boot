package com.example.azure.devops.service;

import com.example.azure.devops.model.Contract;
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

    private CountDownLatch latch = new CountDownLatch(10);
    private Contract payload = null;

    //@KafkaListener(topics = "${test.topic}")
    @KafkaListener(topics = "test")
    public void receive(Contract contract) {
        System.out.println("FINALLY received payload for contract name " + contract.getName());
        log.info("received payload for contract name ='{}'", contract.getName());
        setPayload(contract);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }


}