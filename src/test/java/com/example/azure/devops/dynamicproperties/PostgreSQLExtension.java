package com.example.azure.devops.dynamicproperties;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public class PostgreSQLExtension implements BeforeAllCallback, AfterAllCallback {


    private PostgreSQLContainer<?> postgres;

    @Override
    public void beforeAll(ExtensionContext context) {
        postgres = new PostgreSQLContainer<>("postgres:11")
                .withDatabaseName("integration-tests-db")
                .withUsername("sa")
                .withPassword("sa")
                .withExposedPorts(5432);

        postgres.start();
        String jdbcUrl = String.format("jdbc:postgresql://localhost:%d/integration-tests-db", postgres.getFirstMappedPort());
        System.setProperty("spring.datasource.url", jdbcUrl);
        System.setProperty("spring.datasource.username", "sa");
        System.setProperty("spring.datasource.password", "sa");
    }

    @Override
    public void afterAll(ExtensionContext context) {
        // do nothing, Testcontainers handles container shutdown
    }
}