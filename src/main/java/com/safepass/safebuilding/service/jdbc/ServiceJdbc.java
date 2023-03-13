package com.safepass.safebuilding.service.jdbc;

import com.safepass.safebuilding.common.jdbc.Jdbc;
import com.safepass.safebuilding.common.meta.ServiceStatus;
import com.safepass.safebuilding.service.entity.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
public class ServiceJdbc extends Jdbc {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Service> searchService(String query) {
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            Service service = new Service();
            service.setId(UUID.fromString(rs.getString("id")));
            service.setName(rs.getString("name"));
            service.setDescription(rs.getString("description"));
            service.setPrice(rs.getInt("price"));
            service.setIcon(rs.getString("icon"));
            service.setStatus(ServiceStatus.valueOf(rs.getString("status")));
            return service;
        });
    }
}
