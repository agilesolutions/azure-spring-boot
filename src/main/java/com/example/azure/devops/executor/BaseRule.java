package com.example.azure.devops.executor;

public interface BaseRule extends BaseRuleContext {

    default boolean isStopped() {
        return stop;
    }

    public abstract  <T extends BaseRuleContext>  void execute(T  context);
}
