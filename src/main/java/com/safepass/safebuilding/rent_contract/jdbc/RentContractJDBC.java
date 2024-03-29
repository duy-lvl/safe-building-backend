package com.safepass.safebuilding.rent_contract.jdbc;

import com.safepass.safebuilding.common.jdbc.Jdbc;
import com.safepass.safebuilding.common.meta.RentContractStatus;
import com.safepass.safebuilding.rent_contract.dto.RentContractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

@Component
@CacheConfig(cacheNames = {"contract"})
public class RentContractJDBC extends Jdbc {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public boolean uploadRentContract(String query) {

        return jdbcTemplate.update(query) > 0;
    }

    @CachePut(key = "#query")
    public List<RentContractDTO> getList(String query) {
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            RentContractDTO rentContractDTO = new RentContractDTO();
            rentContractDTO.setId(rs.getString("id"));
            rentContractDTO.setBuildingName(rs.getString("building_name"));
            rentContractDTO.setCustomerName(rs.getString("customer_name"));
            rentContractDTO.setRoomNumber(rs.getInt("room_number"));
            rentContractDTO.setExpiryDate(sdf.format(rs.getDate("expiry_date")));
            rentContractDTO.setStartDate(sdf.format(rs.getDate("start_date")));
            rentContractDTO.setStatus(RentContractStatus.valueOf(rs.getString("status")));
            rentContractDTO.setRentContractLink(rs.getString("rent_contract_link"));
            rentContractDTO.setTitle(rs.getString("title"));
            rentContractDTO.setCustomerId(rs.getString("customer_id"));
            rentContractDTO.setFlatId(rs.getString("flat_id"));
            rentContractDTO.setBuildingAddress(rs.getString("address"));
            rentContractDTO.setBuildingId(rs.getString("building_id"));
            rentContractDTO.setValue(rs.getInt("value"));
            return rentContractDTO;
        });

    }

    public boolean insertContract(String query) {
        return jdbcTemplate.update(query) > 0;
    }
}
