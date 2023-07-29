package com.example.azure.devops.dao;

import org.apache.avro.specific.SpecificRecordBase;

public abstract class BaseDao<T extends SpecificRecordBase> {


    public void insertEntity(T entity) {
        String query = getQuery();
    }

    abstract String getQuery();
}
