package com.example.azure.devops.executor;

import org.springframework.stereotype.Component;

@Component
public class MyRule implements BaseRule {


    @Override
    public void execute(BaseRuleContext context) {
        context.setStopped(false);
    }
}
