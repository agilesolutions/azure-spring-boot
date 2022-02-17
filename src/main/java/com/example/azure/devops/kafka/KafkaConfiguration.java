package com.example.azure.devops.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.kafka.properties")
public class KafkaConfiguration {

    private String example;

}
