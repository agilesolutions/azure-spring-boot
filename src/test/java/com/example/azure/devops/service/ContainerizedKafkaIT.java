package com.example.azure.devops.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
public class ContainerizedKafkaIT {

    public static final String MY_TOPIC = "my-topic";
    public static final int NUMBER_OF_MESSAGES = 100;

    @Container
    public static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", () -> {
            return kafkaContainer.getHost() + ":" + kafkaContainer.getFirstMappedPort();
        });
    }

    @Test
    @DisplayName("kafka server should be running")
    void shouldBeRunningKafka() throws Exception {
        System.out.printf("robserver running");
        assertTrue(kafkaContainer.isRunning());
    }

    @Test
    @DisplayName("should send and receive records over kafka")
    void shouldSendAndReceiveMessages() throws Exception {
        var servers = kafkaContainer.getBootstrapServers();
        System.out.printf("robservers: %s%n", servers);

        var props = new Properties();
        props.put("bootstrap.servers", servers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("group.id", "group-1");
        props.put("auto.offset.reset", "earliest");

        var counter = new AtomicInteger(0);
        CountDownLatch waitABit = new CountDownLatch(1);

        new Thread(() -> {
            KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(props);
            kafkaConsumer.subscribe(Arrays.asList(MY_TOPIC));
            while (counter.get()<NUMBER_OF_MESSAGES) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(200);
                records.forEach(record -> {
                    System.out
                            .printf("%d # offset: %d, value = %s%n", counter.incrementAndGet(), record.offset(),
                                    record.value());
                });
            }
            waitABit.countDown();

        }).start();

        try (
                Producer<String, String> producer = new KafkaProducer<>(props)) {
            IntStream.range(0, NUMBER_OF_MESSAGES).forEach(i -> {
                var msg = String.format("my-message-%d", i);
                producer.send(new ProducerRecord<>(MY_TOPIC, msg));
                System.out.println("Sent:" + msg);
            });
        }

        waitABit.await();
        Assert.assertEquals(NUMBER_OF_MESSAGES, counter.get());
    }
}