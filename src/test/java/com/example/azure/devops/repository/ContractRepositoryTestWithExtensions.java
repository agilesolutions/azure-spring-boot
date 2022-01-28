package com.example.azure.devops.repository;

import com.example.azure.devops.dynamicproperties.KafkaExtension;
import com.example.azure.devops.dynamicproperties.PostgreSQLExtension;
import com.example.azure.devops.model.Contract;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(PostgreSQLExtension.class)
@DirtiesContext
class ContractRepositoryTestWithExtensions {

    @Autowired
    ContractRepository contractRepository;

    @Test
    @DisplayName("find contract by name")
    void givenContract1Exists_whenFindContractOnName_thenFindRecord() {

        contractRepository.save(Contract.builder().id(1L).name("test").build());

        Contract contract = contractRepository.findByNameNativeQuery("test");

        assertThat(contractRepository.findByNameNativeQuery("test")).isNotNull();
        assertAll("test contract"
        , () -> assertEquals("test",contract.getName())
        , () -> assertEquals(1L, contract.getId()));

    }

}