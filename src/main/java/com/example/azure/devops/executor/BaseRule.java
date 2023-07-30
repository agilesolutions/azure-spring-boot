package com.example.azure.devops.executor;

public abstract class BaseRule {

    public abstract  <T extends BaseRuleContext>  void execute(T  context);
}
