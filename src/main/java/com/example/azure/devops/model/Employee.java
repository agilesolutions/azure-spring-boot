package com.example.azure.devops.model;

import lombok.Getter;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;
import org.apache.avro.specific.SpecificRecordBase;

@Getter
public class Employee extends SpecificRecordBase implements SpecificRecord {

    private String name;

    @Override
    public Schema getSchema() {
        return null;
    }

    @Override
    public Object get(int field) {
        return null;
    }

    @Override
    public void put(int field, Object value) {

    }


}
