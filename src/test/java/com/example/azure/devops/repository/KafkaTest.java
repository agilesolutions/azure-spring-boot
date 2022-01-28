package com.example.azure.devops.repository;


import com.example.azure.devops.model.Contract;
import com.example.azure.devops.service.KafkaConsumer;
import com.example.azure.devops.service.KafkaProducer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Testcontainers(disabledWithoutDocker = true)
public class KafkaTest {

    @Container
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"))
            .withEnv("CONNECT_BOOTSTRAP_SERVERS", "kafka:9092")
            .withNetworkAliases("kafka");


    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private KafkaProducer producer;

    @Value("${test.topic:embedded-test-topic}")
    private String topic;


    @Test
    public void givenKafkaDockerContainer_whenSendingtoSimpleProducer_thenMessageReceived()
            throws Exception {
        producer.send(topic, Contract.builder().id(1).name("test").build());
        consumer.getLatch().await(10000, TimeUnit.MILLISECONDS);

        assertEquals(10L,consumer.getLatch().getCount());
        assertEquals("test",consumer.getPayload().getName());
    }

    @Test
    public void verifyRunning() {

        assertTrue(kafka.isRunning());

    }

    @Test
    public void verifyServers() {

        assertNotEquals("",kafka.getBootstrapServers());

    }

    @TestConfiguration
    static class KafkaTestContainersConfiguration {

        @Bean
        ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            return factory;
        }

        @Bean
        public ConsumerFactory<Integer, String> consumerFactory() {
            return new DefaultKafkaConsumerFactory<>(consumerConfigs());
        }

        @Bean
        public NewTopic newTopic() {
            return TopicBuilder.name("embedded-test-topic")
                    .compact()
                    .build();
        }

        @Bean
        public Map<String, Object> consumerConfigs() {
            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            props.put(ConsumerConfig.GROUP_ID_CONFIG, "baeldung");
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
            return props;
        }

        @Bean
        public ProducerFactory<String, Contract> producerFactory() {
            Map<String, Object> configProps = new HashMap<>();
            configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers());
            configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "baeldung");
            configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
            return new DefaultKafkaProducerFactory<>(configProps);
        }

        @Bean
        public KafkaTemplate<String, Contract> kafkaTemplate() {
            return new KafkaTemplate<String, Contract>(producerFactory());
        }

        @Bean
        public KafkaConsumer kafkaConsumer() {

            return new KafkaConsumer();
        }

        @Bean
        public KafkaProducer kafkaProducer() {

            return new KafkaProducer(kafkaTemplate());
        }


    }

}