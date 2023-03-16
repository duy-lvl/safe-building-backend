package com.safepass.safebuilding.dashboard.jdbc;

import com.safepass.safebuilding.dashboard.dto.ServiceStatistics;
import com.safepass.safebuilding.dashboard.dto.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardJdbc {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Statistics> getContractStartDate(String query) {
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            int key = rs.getInt("month");
            int value = rs.getInt("quantity");
            return new Statistics(key, value);
        });
    }
    public List<Statistics> getBillDate(String query) {
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            int key = rs.getInt("month");
            int value = rs.getInt("total");
            return new Statistics(key, value);
        });
    }
    public List<ServiceStatistics> getService(String query) {
        return jdbcTemplate.query(query, (rs, rowNum) -> {
           String name = rs.getString("name");
           int quantity = rs.getInt("quantity");
           int value = rs.getInt("value");
           return new ServiceStatistics(name, quantity, value);
        });
    }
}
