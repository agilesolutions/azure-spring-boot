package com.example.azure.devops.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractServiceConfiguration {

    @Value("com.host.test:http://localhost:8089")
    String host;

    @Bean
    public ContractService  contractService() {

        return new ContractService(restTemplateBuilder(), host );

    }

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {

        return new RestTemplateBuilder();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder().findAndAddModules().build();
    }


}
