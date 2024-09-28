package com.example.azure.devops.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.List;

public class CustomerDao extends BaseDao<Customer> {

    private static final String SELECT_CUSTOMER = "select * from customer where name = :name";

    private String name;

    @Override
    protected void getCustomParams(MapSqlParameterSource params) {
        params.addValue("id", name);

    }

    @Override
    protected String getQuery() {
        return SELECT_CUSTOMER;
    }

    @Override
    protected RowMapper<Customer> getRowMapper() {
        return (rs, i) -> {

            Customer customer = new Customer();
            customer.setId(rs.getInt("id"));
            customer.setName(rs.getString("name"));

            return customer;

        };
    }

    public List<Customer> getCustomerByName(String name) {

        this.name = name;

        return execute();

    }
}
