package com.example.azure.devops.service;

import com.example.azure.devops.model.Contract;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ContractService {

    private final RestTemplate restTemplate;

    @Value("com.host.test")
    private String host;

    public ContractService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.rootUri(host).build();
    }

    public Contract searchContract(int id) {

        return restTemplate.getForObject("http://localhost:8089/{id}/details",Contract.class, id);

    }

}
