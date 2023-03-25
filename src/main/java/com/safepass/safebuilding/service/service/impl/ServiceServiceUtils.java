package com.safepass.safebuilding.service.service.impl;

import com.safepass.safebuilding.building.dto.BuildingGetRequest;

public class ServiceServiceUtils {
    public static String appendSearchQuery(String searchKey) {
        if (searchKey == null) {
            searchKey = "";
        }
        String numberRegex = "\\d+";
        StringBuilder sb = new StringBuilder();
        sb.append(" WHERE name LIKE '%").append(searchKey).append("%' ");

        if (searchKey != null && searchKey.matches(numberRegex)) {

            sb.append(" OR price = ").append(searchKey).append(" ");
        }

        sb.append(" OR status LIKE '%").append(searchKey).append("%' ");
        return sb.toString();
    }

    /**
     * append sort query
     *
     * @param sortBy {@code String}
     * @param order {@code String}
     * @return String
     */
    public static String appendSortQuery(String sortBy, String order) {
        boolean isSortByValid = ("name").equalsIgnoreCase(sortBy) || ("price").equalsIgnoreCase(sortBy) || ("status").equalsIgnoreCase(sortBy);
        boolean isOrderValid = ("ASC").equalsIgnoreCase(order) || ("DESC").equalsIgnoreCase(order);
        if (!isSortByValid || !isOrderValid) {
            return " ORDER BY name ASC ";
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
    public static String appendPagination(int page, int size) {
        return " LIMIT " + size + " OFFSET " + page * size;
    }

    public static String getQuery() {
        return "SELECT id, name, description, price, icon, status FROM service ";
    }

    public static String getTotalRow() {
        return "SELECT count(id) FROM service ";
    }
}
