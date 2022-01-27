package com.example.azure.devops.service;

import com.jupitertools.springtestkafka.KafkaTestContainer;
import com.jupitertools.springtestkafka.expected.annotation.EnableKafkaTest;
import com.jupitertools.springtestkafka.expected.annotation.ExpectedMessages;
import lombok.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.TestPropertySource;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@KafkaTestContainer
//@EnableKafkaTest(topics = "integer-key-test-topic",
 //       testConsumerConfig = IntegerKeyKafkaConsumerConfig.class)
@TestPropertySource(properties = {
        "spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer",
        "spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.IntegerSerializer"
})
class RedisTestContainerTest {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    @ExpectedMessages(topic = "integer-key-test-topic", datasetFile = "/datasets/expected_without_class_ref.json")
    void withIntKeys() {
        kafkaTemplate.send("test-topic", Foo.builder().value("qwert").build());
        kafkaTemplate.send("test-topic", Bar.builder().name("baaark").build());
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    static class Foo {
        private String value;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    static class Bar {
        private String name;
        private Date time;
    }
}