package com.safepass.safebuilding.building.utils;

import com.safepass.safebuilding.building.entity.BuildingRequest;
import com.safepass.safebuilding.building.entity.Building;
import com.safepass.safebuilding.common.meta.BuildingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class BuildingUtils {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public final String SELECT_BUILDINGS_QUERY = "SELECT DISTINCT id, name, address, status FROM building ";
    public final String COUNT_RECORD_QUERY = "SELECT DISTINCT COUNT(id) AS totalRow FROM safe_building.building ";
    private final RowMapper<Building> rowMapper = (rs, row) -> {
        Building building = new Building();
        building.setId(UUID.fromString(rs.getString("id")));
        building.setName(rs.getString("name"));
        building.setAddress(rs.getString("address"));
        building.setStatus(BuildingStatus.valueOf(rs.getString("status")));
        return building;
    };
    private final RowMapper<Integer> rowCountMapper = (rs, row) -> {
        return rs.getInt("totalRow");
    };


    /**
     * append search query
     *
     * @param building
     * @return String
     */
    public String appendSearchQuery(Building building) {
        String id = building.getId().toString();
        String name = building.getName();
        String address = building.getAddress();
        String status = building.getStatus().toString();

        StringBuilder sb = new StringBuilder();
        if (id != null || name != null || address != null || status != null) {
            sb.append(" WHERE ");
        }
        if (id != null) {
            sb.append(" id LIKE '%").append(id).append("%' ");
            if (name != null || address != null || status != null) {
                sb.append("OR ");
            }
        }
        if (name != null) {
            sb.append(" name LIKE '%").append(name).append("%' ");
            if (address != null || status != null) {
                sb.append("OR ");
            }
        }
        if (address != null) {
            sb.append(" address LIKE '%").append(address).append("%' ");
            if (status != null) {
                sb.append("OR ");
            }
        }
        if (status != null) {
            sb.append(" status LIKE '%").append(status).append("%' ");
        }
        return sb.toString();
    }

    /**
     * append search query
     *
     * @param searchKey
     * @return String
     */
    public String appendSearchQuery(String searchKey) {
        if(searchKey == null){
            searchKey = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" WHERE name LIKE '%").append(searchKey).append("%' ")
                .append(" OR address LIKE '%").append(searchKey).append("%' ")
                .append(" OR status LIKE '%").append(searchKey).append("%' ");
        return sb.toString();
    }

    /**
     * append sort query
     *
     * @param buildingRequest
     * @return String
     */
    public String appendSortQuery(BuildingRequest buildingRequest) {
        String name = buildingRequest.getSortName();
        String address = buildingRequest.getSortAddress();
        String status = buildingRequest.getSortStatus();
        boolean isNameValid = ("ASC").equalsIgnoreCase(name) || ("DESC").equalsIgnoreCase(name);
        boolean isAddressValid = ("ASC").equalsIgnoreCase(address) || ("DESC").equalsIgnoreCase(address);
        boolean isStatusValid = ("ASC").equalsIgnoreCase(status) || ("DESC").equalsIgnoreCase(status);
        if(!isNameValid && !isAddressValid && !isStatusValid){
            return " ";
        }
        StringBuilder sb = new StringBuilder();

        sb.append(" ORDER BY ");


        if (isNameValid) {
            sb.append(" name ").append(name);
            if ((isAddressValid)|| (isStatusValid)) {
                sb.append(", ");
            }
        }
        if (isAddressValid) {
            sb.append(" address ").append(address);
            if (isStatusValid) {
                sb.append(", ");
            }
        }
        if (isStatusValid) {
            sb.append(" status ").append(status);
        }
        return sb.toString();
    }

    /**
     * append pagination
     *
     * @param page
     * @param size
     * @return String
     */
    public String appendPagination(int page, int size) {
        return " LIMIT " + size + " OFFSET " + page * size;
    }

    /**
     * Get total records
     *
     * @param query
     * @return int
     */
    public int getTotalRow(String query) {
        return jdbcTemplate.query(query, rowCountMapper).get(0);
    }

    /**
     * Get building list
     *
     * @param query
     * @return List<Building></>
     */
    public List<Building> getBuildingList(String query) {
        return jdbcTemplate.query(query, rowMapper);
    }
}
