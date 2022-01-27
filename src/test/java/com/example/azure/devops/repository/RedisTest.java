package com.example.azure.devops.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@Testcontainers(disabledWithoutDocker = true)
public class RedisTest {

    @Container
    public static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:11.1")
            //.withInitScript("load-contracts.sql")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");



    @Test
    public void verifyRunning() {

        assertTrue(postgreSQLContainer.isRunning());

    }
}