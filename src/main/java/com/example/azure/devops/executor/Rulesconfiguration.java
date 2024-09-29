package com.example.azure.devops.executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Rulesconfiguration {


    @Bean
    public RuleExecutor myexecutor(MyRule myRule, CancelRule cancelRule, YourRule yourRule) {

        return new RuleExecutor(myRule, cancelRule, yourRule);

    }
}
