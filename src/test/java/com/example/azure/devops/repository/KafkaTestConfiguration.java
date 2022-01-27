package com.example.azure.devops.repository;


import com.example.azure.devops.service.KafkaConsumer;
import com.example.azure.devops.service.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@Configuration
public class KafkaTestConfiguration {

    @Bean
    public KafkaConsumer kafkaConsumer() {

        return new KafkaConsumer();
    }

    @Bean
    public KafkaProducer kafkaProducer() {

        return new KafkaProducer();
    }


}