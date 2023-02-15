package com.safepass.safebuilding.customer.service.impl;

public class CustomerServiceUtil {

    public static String constructQueryForGetAllCustomer(int page, int size) {
        return "SELECT building.name AS building_name, flat.room_number AS room_number, customer.phone, \n" +
                "\tcustomer.fullname, customer.citizen_id, customer.status AS customer_status\n" +
                "FROM building JOIN flat ON building.id = flat.building_id \n" +
                "\tJOIN rent_contract ON rent_contract.flat_id = flat.id\n" +
                "    JOIN customer ON customer.id = rent_contract.customer_id\n" +
                "WHERE building.status = \"AVAILABLE\" AND flat.status = \"AVAILABLE\" \n" +
                "\tAND rent_contract.status = \"VALID\" AND customer.status = \"ACTIVE\"\n" +
                "ORDER BY building.name ASC, flat.room_number ASC, customer.status ASC"
                +" LIMIT " + size + " OFFSET " + page * size;
    }

    public static String constructQueryForGetTotalRowGetAllCustomer() {
        return "SELECT count(building.name) as total\n" +
                "FROM building JOIN flat ON building.id = flat.building_id \n" +
                "\tJOIN rent_contract ON rent_contract.flat_id = flat.id\n" +
                "    JOIN customer ON customer.id = rent_contract.customer_id\n" +
                "WHERE building.status = \"AVAILABLE\" AND flat.status = \"AVAILABLE\" \n" +
                "\tAND rent_contract.status = \"VALID\" AND customer.status = \"ACTIVE\"\n" +
                "ORDER BY building.name ASC, flat.room_number ASC, customer.status ASC";
    }

    public static String construcQueryForGetCustomerDevice(int page, int size) {
        return "SELECT DISTINCT customer_id, fullname\n" +
                "FROM device JOIN customer ON device.customer_id = customer.id\n" +
                "LIMIT " + size + " OFFSET " + (page * size);
    }

    public static String constructQueryForGetTotalRowGetCustomerDevice() {
        return "SELECT count(DISTINCT customer_id)" +
                "FROM device JOIN customer ON device.customer_id = customer.id;";
    }
}
