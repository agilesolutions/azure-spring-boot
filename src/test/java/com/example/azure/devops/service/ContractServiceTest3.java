package com.example.azure.devops.service;

import com.example.azure.devops.dynamicproperties.KafkaExtension;
import com.example.azure.devops.repository.ContractRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(KafkaExtension.class)
@DirtiesContext
class ContractServiceTest3 {

    @Autowired
    ContractService contractService;


    @Test
    void searchContract() {

        contractService.searchContract(1);

    }
}