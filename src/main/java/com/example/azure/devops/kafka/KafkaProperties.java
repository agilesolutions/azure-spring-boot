package com.example.azure.devops.kafka;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class KafkaProperties {

    @Value("${bootstrap.servers}")
    String servers;


    @Value("${kafka.topics.topic1}")
    String topic1;


}
