package com.example.azure.devops.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public abstract class BaseDao <T extends BaseEntity>{

    NamedParameterJdbcTemplate template;

    private MapSqlParameterSource params;

    protected List<T> execute() {

         params = getBaseParams();

        // Hollywood principle
        getCustomParams(getBaseParams());

        // Hollywood principle
        return executeQuery(getQuery());

    }

    protected abstract void getCustomParams(MapSqlParameterSource params);

    protected abstract String getQuery();

    protected abstract RowMapper getRowMapper();


    private MapSqlParameterSource getBaseParams() {

        return new MapSqlParameterSource("name", "Nam Ha Minh");
    }

    private List<T> executeQuery(String query) {

        return template.query(query, params, getRowMapper());

    }


}
