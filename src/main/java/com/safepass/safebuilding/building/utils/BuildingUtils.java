package com.safepass.safebuilding.building.utils;

import com.safepass.safebuilding.building.dto.BuildingGetRequest;
import com.safepass.safebuilding.building.entity.Building;
import com.safepass.safebuilding.common.meta.BuildingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@CacheConfig(cacheNames = {"building"})
public class BuildingUtils {
    public final String SELECT_BUILDINGS_QUERY = "SELECT DISTINCT id, name, address, status, capacity FROM building ";
    public final String COUNT_RECORD_QUERY = "SELECT DISTINCT COUNT(id) AS totalRow FROM safe_building.building ";
    private final RowMapper<Building> rowMapper = (rs, row) -> {
        Building building = new Building();
        building.setId(UUID.fromString(rs.getString("id")));
        building.setName(rs.getString("name"));
        building.setAddress(rs.getString("address"));
        building.setStatus(BuildingStatus.valueOf(rs.getString("status")));
        building.setCapacity(rs.getInt("capacity"));
        return building;
    };
    private final RowMapper<Integer> rowCountMapper = (rs, row) -> {
        return rs.getInt("totalRow");
    };
    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        if (searchKey == null) {
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
     * @param buildingGetRequest
     * @return String
     */
    public String appendSortQuery(BuildingGetRequest buildingGetRequest) {
        String sortBy = buildingGetRequest.getSortBy();
        String order = buildingGetRequest.getOrder();
        boolean isSortByValid = ("name").equalsIgnoreCase(sortBy) || ("address").equalsIgnoreCase(sortBy) || ("status").equalsIgnoreCase(sortBy);
        boolean isOrderValid = ("ASC").equalsIgnoreCase(order) || ("DESC").equalsIgnoreCase(order);
        if (!isSortByValid || !isOrderValid) {
            return " ";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" ORDER BY ").append(sortBy).append(" ").append(order).append(" ");
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
     * @return List<Building>
     */
    @CachePut(key = "#query")
    public List<Building> getBuildingList(String query) {
        return jdbcTemplate.query(query, rowMapper);
    }
}
