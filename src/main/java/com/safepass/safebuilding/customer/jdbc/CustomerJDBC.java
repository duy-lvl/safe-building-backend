package com.safepass.safebuilding.customer.jdbc;


import com.safepass.safebuilding.common.meta.CustomerStatus;
import com.safepass.safebuilding.customer.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CustomerJDBC {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CustomerDTO> getAllCustomer(String query) {
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCitizenId(rs.getString("citizen_id"));
            customerDTO.setFullname(rs.getString("fullname"));
            customerDTO.setBuildingName(rs.getString("building_name"));
            customerDTO.setPhone(rs.getString("phone"));
            customerDTO.setRoomNumber(rs.getInt("room_number"));
            customerDTO.setStatus(CustomerStatus.valueOf(rs.getString("customer_status")));
            return  customerDTO;
        });
    }
    public Long getTotalRow(String query) {
        return jdbcTemplate.queryForObject(query, Long.class);
    }

}
