package com.example.azure.devops.service;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.example.azure.devops.model.Contract;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(PactConsumerTestExt.class)
@ExtendWith(SpringExtension.class)
@PactTestFor(providerName = "contractProvider", port = "8089")
@ActiveProfiles("test")
@ContextConfiguration(classes = {ContractServiceConfiguration.class})
class ContractServiceTest {

    @Autowired
    ContractService contractService;

    @Autowired
    ObjectMapper objectMapper;

    private Map<String, String> headers = new HashMap<>();


    @Pact(provider = "contractProvider", consumer = "contractConsumer")
    public RequestResponsePact createPactForSearchingContract(PactDslWithProvider builder) throws JsonProcessingException {

        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");

        return builder
                .given("Contract opened")
                .uponReceiving("Request for opened contract")
                .path("/1/details")
                .method("GET")
                .headers(headers)
                .willRespondWith()
                .status(200)
                .body(objectMapper.writeValueAsString(Contract.builder().id(1).name("test").build()))
                .toPact();

    }

    @Test
    @PactTestFor(providerName = "contractProvider", port = "8089")
    void givenContractOpen_whenSelectingFirstContract_thenReturnFirstContract() {

        final Contract contract = contractService.searchContract(1);

    }
}