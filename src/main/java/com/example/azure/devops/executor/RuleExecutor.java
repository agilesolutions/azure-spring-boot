package com.example.azure.devops.executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RuleExecutor <T extends BaseRule> {

    private List<T> rules = new ArrayList<T>();

    public RuleExecutor(T ... args) {

        Collections.addAll(rules, args);
    }

    public <C extends BaseRuleContext> void execute(C context) {

        for(T rule:rules) {
                 rule.execute(context);
                 if (context.isStop()) break;
        }



    }

}
