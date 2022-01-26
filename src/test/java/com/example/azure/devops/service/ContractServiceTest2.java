package com.example.azure.devops.service;

import com.example.azure.devops.model.Contract;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

//@RestClientTest({ContractService.class})
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContractServiceTest2 {

    @Autowired
    ContractService contractService;

    @Autowired
    MockRestServiceServer mockServer;

    @Autowired
    ObjectMapper objectMapper;

    //@AfterEach
    public void resetMockServer() {
        mockServer.reset();
    }

    //@Test
    void givenContractOpen_whenSelectingFirstContract_thenReturnFirstContract() throws JsonProcessingException {

        mockServer.expect(requestTo("/1/details"))
                .andRespond(withSuccess(objectMapper.writeValueAsString(Contract.builder().id(1).name("test").build()), MediaType.APPLICATION_JSON));

        var contract = contractService.searchContract(1);

        assertAll("verify contract"
        , () -> assertEquals(true, contract.isPresent()));

    }
}