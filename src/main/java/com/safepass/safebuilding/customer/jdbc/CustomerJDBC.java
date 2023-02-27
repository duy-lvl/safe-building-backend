package com.safepass.safebuilding.customer.jdbc;


import com.safepass.safebuilding.common.jdbc.Jdbc;
import com.safepass.safebuilding.common.meta.CustomerStatus;
import com.safepass.safebuilding.customer.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CustomerJDBC extends Jdbc {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<CustomerDTO> getCustomerList(String query) {
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerId(rs.getString("customer_id"));
            customerDTO.setCitizenId(rs.getString("citizen_id"));
            customerDTO.setFullname(rs.getString("fullname"));
            customerDTO.setBuildingName(rs.getString("building_name"));
            customerDTO.setPhone(rs.getString("phone"));
            customerDTO.setRoomNumber(rs.getInt("room_number"));
            customerDTO.setStatus(CustomerStatus.valueOf(rs.getString("customer_status")));
            customerDTO.setBuildingId(rs.getString("building_id"));
            return  customerDTO;
        });
    }



}
