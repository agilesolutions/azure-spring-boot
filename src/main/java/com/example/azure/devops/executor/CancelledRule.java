package com.example.azure.devops.executor;

import org.springframework.stereotype.Component;

@Component
public class CancelledRule implements BaseRule{


    @Override
    public void execute(BaseRuleContext context) {
        context.setStopped(true);
    }
}
