package com.example.azure.devops.executor;

public class MyRule implements BaseRule{
    @Override
    public <T extends BaseRuleContext> void execute(T context) {

        stop = context.isStop();

    }
}
