package com.example.azure.devops.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractServiceConfiguration {

    @Bean
    public ContractService  contractService() {

        return new ContractService(restTemplateBuilder() );

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
