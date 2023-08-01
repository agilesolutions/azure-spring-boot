package com.example.azure.devops.dao;

import lombok.Data;

@Data
public class Customer extends BaseEntity {

    private int id;
    private String name;
}
