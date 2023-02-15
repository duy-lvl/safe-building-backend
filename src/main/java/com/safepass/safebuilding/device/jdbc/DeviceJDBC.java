package com.safepass.safebuilding.device.jdbc;

import com.safepass.safebuilding.device.dto.DeviceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
@Component
public class DeviceJDBC {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<DeviceDTO> getAllDevice(String query) {
        return jdbcTemplate.query(query, (rs, rowNum) -> {
           DeviceDTO device = new DeviceDTO();
           device.setFullname(rs.getString("fullname"));
           device.setToken(rs.getString("token"));
           device.setCustomerId(UUID.fromString(rs.getString("customer_id")));
           return device;
        });
    }

    public Long getTotalRow(String query) {
        return jdbcTemplate.queryForObject(query, Long.class);
    }
}
