package com.example.azure.devops.executor;

public abstract class BaseRule extends BaseRuleContext {

    abstract boolean isStopped();

    public abstract  <T extends BaseRuleContext>  void execute(T  context);
}
