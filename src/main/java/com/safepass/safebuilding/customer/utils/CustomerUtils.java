package com.safepass.safebuilding.customer.utils;

import com.safepass.safebuilding.customer.dto.RequestObjectForFilter;

public class CustomerUtils {

    private static final String queryGet = "FROM customer WHERE ";

    public static String constructQueryForGetTotalRowGetAllCustomer() {
        return "SELECT count(customer.id) as total\n" +
                queryGet;
    }

    public static String constructQueryForFilter(RequestObjectForFilter requestObjectForFilter, int page, int size) {
        String query = "SELECT id AS customer_id, phone, \n"
                + "\tfullname, citizen_id, status AS customer_status\n"
                + queryGet;

        query +=  appendSearchKey(requestObjectForFilter.getSearchKey());
        query += appendSort(requestObjectForFilter.getSortStatus())
                + " LIMIT " + size + " OFFSET " + page * size;
        return query;
    }

    public static String filterTotalRow(RequestObjectForFilter requestObjectForFilter) {
        return "SELECT count(id) as total\n" +
                queryGet
                + appendSearchKey(requestObjectForFilter.getSearchKey());
    }

    public static String getContracts(String customerId) {
        return "SELECT flat.room_number AS room_number, rent_contract.contract AS link, rent_contract.status\n" +
                "FROM building JOIN flat ON building.id = flat.building_id\n" +
                "            JOIN rent_contract ON rent_contract.flat_id = flat.id\n" +
                "WHERE customer_id = '" + customerId + "'";
    }

    private static StringBuilder appendSearchKey(String searchKey) {
        if (searchKey == null) {
            searchKey = "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("  (fullname LIKE '%").append(searchKey)
                .append("%' OR phone LIKE '%").append(searchKey)
                .append("%' OR citizen_id LIKE '%").append(searchKey)
                .append("%')\n");

        return sb;
    }

    private static String appendSort(String sortStatus) {

        String result = " ";
        if (sortStatus == null || sortStatus.isBlank()) {
            return result;
        }
        sortStatus = sortStatus.toUpperCase();
        if (!"ASC".equals(sortStatus) && !"DESC".equals(sortStatus)) {
            return result;
        }
        return " ORDER BY status " + sortStatus + " ";
    }
}
