package com.safepass.safebuilding.customer.jdbc;


import com.safepass.safebuilding.common.jdbc.Jdbc;
import com.safepass.safebuilding.common.meta.CustomerStatus;
import com.safepass.safebuilding.common.meta.RentContractStatus;
import com.safepass.safebuilding.customer.dto.ContractDTO;
import com.safepass.safebuilding.customer.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@CacheConfig(cacheNames = {"customer"})
public class CustomerJDBC extends Jdbc {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @CachePut(key = "#query")
    public List<CustomerDTO> getCustomerList(String query) {
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(UUID.fromString(rs.getString("customer_id")));
            customerDTO.setCitizenId(rs.getString("citizen_id"));
            customerDTO.setFullname(rs.getString("fullname"));
            customerDTO.setPhone(rs.getString("phone"));
            customerDTO.setStatus(CustomerStatus.valueOf(rs.getString("customer_status")));
            return customerDTO;
        });
    }

    @CachePut(key = "#query")
    public List<ContractDTO> getContracts(String query) {
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            ContractDTO contractDTO = new ContractDTO();
            contractDTO.setRoomNumber(rs.getInt("room_number"));
            contractDTO.setLink(rs.getString("link"));
            contractDTO.setStatus(RentContractStatus.valueOf(rs.getString("status")));
            contractDTO.setTitle(rs.getString("title"));
            contractDTO.setBuildAddress(rs.getString("address"));
            contractDTO.setBuildingName(rs.getString("name"));
            contractDTO.setId(rs.getString("id"));
            return contractDTO;
        });
    }

}
