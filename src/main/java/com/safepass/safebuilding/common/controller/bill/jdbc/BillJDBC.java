package com.safepass.safebuilding.common.controller.bill.jdbc;

import com.safepass.safebuilding.common.controller.bill.dto.BillDTO;
import com.safepass.safebuilding.common.jdbc.Jdbc;
import com.safepass.safebuilding.common.meta.BillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.UUID;
@Component
public class BillJDBC extends Jdbc {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<BillDTO> getBill(String query) {
        return jdbcTemplate.query(query, (rs, rowNumber) -> {
            BillDTO billDTO = new BillDTO();
            billDTO.setId(UUID.fromString(rs.getString("id")));
            billDTO.setStatus(BillStatus.valueOf(rs.getString("status")));
            billDTO.setDate(Date.valueOf(rs.getString("date")));
            billDTO.setValue(rs.getInt("value"));
            return billDTO;
        });
    }
}
