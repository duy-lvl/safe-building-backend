package com.safepass.safebuilding.common.excel.utils;

import com.safepass.safebuilding.common.excel.entity.Info;
import org.checkerframework.checker.lock.qual.NewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ExcelUtils {
    private final RowMapper<Info> rowMapper = (rs, row) -> {
        Info info = new Info();
        info.setContractId(UUID.fromString(rs.getString("contract_id")));
        info.setCustomerId(UUID.fromString(rs.getString("customer_id")));
        info.setFlatId(UUID.fromString(rs.getString("flat_id")));
        info.setRoomNumber(Integer.parseInt(rs.getString("room_number")));
        return info;
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Info> getListInfoForExcel(UUID buildingId) {
        String query = "SELECT rc.id AS contract_id, rc.customer_id, rc.flat_id, f.room_number FROM safe_building.rent_contract rc\n" +
                "JOIN safe_building.flat f ON f.id = rc.flat_id\n" +
                "JOIN safe_building.building b ON b.id = f.building_id \n" +
                "WHERE b.id = '"+buildingId+"'\n" +
                "AND rc.status = 'VALID' \n" +
                "OR (YEAR(rc.expiry_date) = YEAR(CURDATE()) \n" +
                "AND MONTH(rc.expiry_date)=MONTH(CURDATE()))\n" +
                "ORDER BY f.room_number ASC;";
        return jdbcTemplate.query(query ,rowMapper);
    }
}
