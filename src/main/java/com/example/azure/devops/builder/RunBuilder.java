package com.example.azure.devops.builder;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class RunBuilder {

    public static void main(String[] args) {

        new RunBuilder().build();

    }

    private void build() {

        List list = Arrays.asList(1, 2, 3, 4, 5)

        System.out.println(
                Builder.build(Sample.class)
                        .with(s -> s.setId(1))
                        .with(s -> s.setName("Sample object"))
                        .with(s -> s.setList(list))
                        .get()
        );

// Java Properties
        System.out.println(
                Builder.build(Properties.class)
                        .with(p -> p.put("one", 1))
                        .with(p -> p.put("two", 2))
          .get());


        System.out.println(Builder.build(Properties.class)
                .with(p -> p.put("one", 1))
                .If(() -> false) // any expression return boolean
                .with(p -> p.put("two", 2))
                .with(p -> p.put("three", 3))
                .endIf()
                .with(p -> p.put("four", 4))
                .get()
        );



    }
}
