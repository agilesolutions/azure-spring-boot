package com.example.azure.devops.executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Rulesconfiguration {


    @Bean
    public RuleExecutor<BaseRule> myexecutor() {

        return new RuleExecutor<>( new MyRule(), new CancelledRule(), new YourRule());

    }
}
