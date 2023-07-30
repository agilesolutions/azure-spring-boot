package com.example.azure.devops.executor;

public abstract class BaseRule extends BaseRuleContext {

    boolean isStopped() {
        return stop;
    }

    public abstract  <T extends BaseRuleContext>  void execute(T  context);
}
