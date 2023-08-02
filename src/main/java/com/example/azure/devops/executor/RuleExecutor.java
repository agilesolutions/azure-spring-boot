package com.example.azure.devops.executor;

import java.util.Arrays;
import java.util.List;

public class RuleExecutor <T extends BaseRuleContext> {

    private List<BaseRule<T>> rules;

    public RuleExecutor(BaseRule<T> ... rules) {

        this.rules = Arrays.asList(rules);
    }

    public <C extends BaseRuleContext> void execute(T context) {

        for(BaseRule<T> rule:rules) {
                 rule.execute(context);
                 if (rule.isStopped(context) || context.isStopped()) break;
        }



    }

}
