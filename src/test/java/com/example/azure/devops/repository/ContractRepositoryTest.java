package com.example.azure.devops.repository;

import com.example.azure.devops.model.Contract;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
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

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ContextConfiguration(initializers = {ContractRepositoryTest.Initializer.class})
@Testcontainers(disabledWithoutDocker = true)
class ContractRepositoryTest {

    @Autowired
    ContractRepository contractRepository;

    @Container
    public static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:11.1")
            //.withInitScript("load-contracts.sql")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        propertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        propertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

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

    @Test
    @DisplayName("find all contracts")
    void givenContract1Exists_whenFindAllContracts_thenFindOneRecord() {

        contractRepository.save(Contract.builder().id(1L).name("test").build());

        List<Contract> contracts = contractRepository.findAll();

        assertThat(contractRepository.findByNameNativeQuery("test")).isNotNull();
        assertAll("test contracts"
                , () -> assertEquals(1, contracts.size()));
    }


    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}