package com.safepass.safebuilding.common.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class Jdbc {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public Long getTotalRow(String query) {
        return jdbcTemplate.queryForObject(query, Long.class);
    }
}
