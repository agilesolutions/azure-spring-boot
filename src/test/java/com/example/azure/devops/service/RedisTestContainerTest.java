package com.example.azure.devops.service;

import com.jupitertools.springtestkafka.KafkaTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@KafkaTestContainer
class RedisTestContainerTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void sendAndReceiveTest() throws InterruptedException {
        assertThat(kafkaTemplate).isNotNull();
        kafkaTemplate.send("test-topic", "flight of a dragon");

    }
}