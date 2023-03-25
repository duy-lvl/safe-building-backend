package com.safepass.safebuilding.flat.jdbc;

import com.safepass.safebuilding.common.jdbc.Jdbc;
import com.safepass.safebuilding.flat.dto.AvailableFlatDTO;
import com.safepass.safebuilding.flat.dto.FlatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@CacheConfig(cacheNames = {"flat"})
public class FlatJDBC extends Jdbc {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @CachePut(key = "#query")
    public List<FlatDTO> getFlatList(String query) {
        return jdbcTemplate.query(query, (rs, rowNum) -> {
           FlatDTO flat = new FlatDTO();
           flat.setId(rs.getString("id"));
           flat.setBuildingName(rs.getString("building_name"));
           flat.setRoomNumber(rs.getInt("room_number"));
           flat.setPrice(rs.getInt("price"));
           flat.setFlatType(rs.getString("flat_type"));
           flat.setStatus(rs.getString("status"));
           return flat;
        });
    }

    public boolean updateStatus(String query) {
        return jdbcTemplate.update(query) > 0;
    }

    public List<AvailableFlatDTO> getFlatByBuildingId(String query) {
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            AvailableFlatDTO flatDTO = new AvailableFlatDTO();
            flatDTO.setId(rs.getString("id"));
            flatDTO.setRoomNumber(rs.getInt("room_number"));
            flatDTO.setValue(rs.getInt("price"));
            return flatDTO;
        });
    }
}
