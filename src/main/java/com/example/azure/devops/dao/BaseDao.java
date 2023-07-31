package com.example.azure.devops.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public abstract class BaseDao <T extends BaseEntity>{

    NamedParameterJdbcTemplate template;

    private String query;

    private MapSqlParameterSource params;

    public void execute(T entity) {

         params = setBaseParams();

        // Hollywood principle
        setCustomParams(params);

        // Hollywood principle
        query = getQuery();

        executeQuery(query);

    }

    public abstract void setCustomParams(MapSqlParameterSource params);

    public abstract String getQuery();

    private MapSqlParameterSource setBaseParams() {

        return new MapSqlParameterSource("name", "Nam Ha Minh");
    }

    private List<T> executeQuery(String query) {


        return template.query(query, params, (rs,s) -> {

           List<T> results = new ArrayList<>();

           T record = new T();



           results.add()

           return results;

        });



    }


}
