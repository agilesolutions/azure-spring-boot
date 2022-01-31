package com.example.azure.devops.service;

import com.example.azure.devops.model.Contract;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, Contract> kafkaTemplate;

    public void send(String topic, Contract payload) {
        System.out.println(String.format("START sending payload %s to topc %s", payload, topic));
        log.info("sending payload='{}' to topic='{}'", payload, topic);
        ListenableFuture<SendResult<String, Contract>> future = kafkaTemplate.send(topic, payload);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Contract>>() {

            @Override
            public void onSuccess(SendResult<String, Contract> result) {
                System.out.println("Sent message=[" + payload.getName() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + payload.getName() + "] due to : " + ex.getMessage());
            }
        });

        System.out.println(String.format("FINISHED sending payload %s to topc %s", payload, topic));
    }
}