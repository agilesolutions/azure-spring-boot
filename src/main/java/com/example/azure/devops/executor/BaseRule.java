package com.example.azure.devops.executor;

public interface BaseRule<T extends BaseRuleContext> {

    default boolean isStopped(T context) {
        return context.isStopped();
    };

    void execute(T  context);
}
