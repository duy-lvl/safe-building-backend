package com.safepass.safebuilding.customer.service.impl;

import com.safepass.safebuilding.customer.dto.RequestObjectForFilter;

public class CustomerServiceUtil {

    private static final String queryGet =
            "FROM building JOIN flat ON building.id = flat.building_id \n" +
            "\tJOIN rent_contract ON rent_contract.flat_id = flat.id\n" +
            "    JOIN customer ON customer.id = rent_contract.customer_id\n" +
            "WHERE building.status = \"AVAILABLE\" AND flat.status <> \"AVAILABLE\" \n" +
            "\tAND rent_contract.status = \"VALID\" AND customer.status = \"ACTIVE\"\n";
    public static String constructQueryForGetAllCustomer(int page, int size) {
        return "SELECT customer.id AS customer_id, building.name AS building_name, flat.room_number AS room_number, customer.phone, \n" +
                "\tcustomer.fullname, customer.citizen_id, customer.status AS customer_status, building_id\n" +
                queryGet
                + " ORDER BY building.name ASC, flat.room_number ASC, customer.status ASC "
                + " LIMIT " + size + " OFFSET " + page * size;
    }

    public static String constructQueryForGetTotalRowGetAllCustomer() {
        return "SELECT count(building.name) as total\n" +
                queryGet;
    }

    public static String constructQueryForFilter(RequestObjectForFilter requestObjectForFilter, int page, int size) {
        return "SELECT customer.id AS customer_id, building.name AS building_name, flat.room_number AS room_number, customer.phone, \n" +
                "\tcustomer.fullname, customer.citizen_id, customer.status AS customer_status, building_id\n" +
                queryGet
                + " AND (building.id = '" + requestObjectForFilter.getBuildingId() + "'"
                + "  OR fullname LIKE \"%"+requestObjectForFilter.getSearchString()
                +"%\" OR phone LIKE \"%"+requestObjectForFilter.getSearchString()+"%\")\n"
                + " ORDER BY building.name ASC, flat.room_number ASC, customer.status ASC "
                +" LIMIT " + size + " OFFSET " + page * size;
    }

    public static String filterTotalRow(RequestObjectForFilter requestObjectForFilter) {
        return "SELECT count(building.name) as total\n" +
                queryGet
                + " AND (building.id = '" + requestObjectForFilter.getBuildingId() + "'"
                + "  OR fullname LIKE \"%"+requestObjectForFilter.getSearchString()
                +"%\" OR phone LIKE \"%"+requestObjectForFilter.getSearchString()+"%\")\n";
    }
}
