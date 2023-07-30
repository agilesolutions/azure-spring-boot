package com.example.azure.devops.executor;

public abstract class BaseRule extends BaseRuleContext {

    public abstract  <T extends BaseRuleContext>  void execute(T  context);
}
