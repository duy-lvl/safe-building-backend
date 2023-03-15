package com.safepass.safebuilding.dashboard.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class DashboardJdbc {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<String> getContractStartDate(String query) {
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            return rs.getString("start_date");
        });
    }
}
