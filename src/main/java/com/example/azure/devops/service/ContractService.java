package com.example.azure.devops.service;

import com.example.azure.devops.model.Contract;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ContractService {

    private final RestTemplate restTemplate;

    public ContractService(RestTemplateBuilder restTemplateBuilder, @Value("com.host.test:http://localhost:8089") String host) {
        this.restTemplate = restTemplateBuilder.rootUri(host).build();
    }

    public Optional<Contract> searchContract(int id) {

        ResponseEntity<Contract> response;

        try {
            response = restTemplate.getForEntity("/{id}/details",Contract.class, id);
        } catch (HttpStatusCodeException e) {
            return Optional.empty();
        }

        return Optional.ofNullable(response.getBody());

    }

}
