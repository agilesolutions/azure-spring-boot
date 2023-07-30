package com.example.azure.devops.executor;

public class CancelledRule extends BaseRule{
    @Override
    boolean isStopped() {
        return true;
    }

    @Override
    public <T extends BaseRuleContext> void execute(T context) {

        context.setStop(stop);

    }
}
