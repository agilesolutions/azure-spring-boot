package com.example.azure.devops.executor;

public class YourRule extends BaseRule{
    @Override
    boolean isStopped() {
        return false;
    }

    @Override
    public <T extends BaseRuleContext> void execute(T context) {

        stop = context.isStop();

    }
}
