package com.example.azure.devops.repository;


import com.example.azure.devops.service.KafkaConsumer;
import com.example.azure.devops.service.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Testcontainers(disabledWithoutDocker = true)
public class KafkaTest {

    @Container
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));


    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private KafkaProducer producer;

    @Value("${test.topic}")
    private String topic;


    @Test
    public void givenKafkaDockerContainer_whenSendingtoSimpleProducer_thenMessageReceived()
            throws Exception {
        producer.send(topic, "Sending with own controller");
        consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);

        assertEquals(0L,consumer.getLatch().getCount());
        assertTrue(consumer.getPayload().contains("embedded-test-topic"));
    }

    @Test
    public void verifyRunning() {

        assertTrue(kafka.isRunning());

    }

    @Test
    public void verifyServers() {

        assertNotEquals("",kafka.getBootstrapServers());

    }
}