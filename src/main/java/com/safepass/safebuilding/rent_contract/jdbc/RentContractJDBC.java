package com.safepass.safebuilding.rent_contract.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
@Component
public class RentContractJDBC {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean uploadRentContract(String query) {

        return jdbcTemplate.update(query) > 0;
    }
}
