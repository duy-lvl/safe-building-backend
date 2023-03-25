package com.safepass.safebuilding.wallet.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class WalletJDBC {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public boolean createWallet(String query) {
        return jdbcTemplate.update(query) > 0;
    }
}
