package com.safepass.safebuilding.flat.jdbc;

import com.safepass.safebuilding.common.jdbc.Jdbc;
import com.safepass.safebuilding.flat.dto.FlatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlatJDBC extends Jdbc {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<FlatDTO> getFlatList(String query) {
        return jdbcTemplate.query(query, (rs, rowNum) -> {
           FlatDTO flat = new FlatDTO();
           flat.setBuildingName(rs.getString("building_name"));
           flat.setRoomNumber(rs.getInt("room_number"));
           flat.setPrice(rs.getInt("price"));
           flat.setFlatType(rs.getString("flat_type"));
           flat.setStatus(rs.getString("status"));
           return flat;
        });
    }


}
