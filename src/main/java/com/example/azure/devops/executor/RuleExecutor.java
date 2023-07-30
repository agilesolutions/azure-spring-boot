package com.example.azure.devops.executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class RuleExecutor <T extends BaseRule> {

    private List<T> rules = new ArrayList<T>();

    public RuleExecutor(T ... args) {

        Collections.addAll(rules, args);
    }

    public void execute(T context) {

        for(T rule:rules) {
            if (!rule.isStopped())
                 rule.execute(context);
        }



    }

}
