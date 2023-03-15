package com.safepass.safebuilding.dashboard.jdbc;

import com.safepass.safebuilding.dashboard.dto.BillDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class DashboardJdbc {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<String> getContractStartDate(String query) {
        return jdbcTemplate.query(query, (rs, rowNum) -> rs.getString("start_date"));
    }
    public List<BillDTO> getBillDate(String query) {
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            int value = rs.getInt("value");
            String date = rs.getString("date");
            return new BillDTO(date, value);
        });
    }
}
