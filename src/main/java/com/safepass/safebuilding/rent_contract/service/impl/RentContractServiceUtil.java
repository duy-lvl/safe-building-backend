package com.safepass.safebuilding.rent_contract.service.impl;

public class RentContractServiceUtil {

    public static String constructQuery(String id, String customerId, String flatId, String url) {
        return "UPDATE rent_contract\n" +
                "SET contract = '" + url +"'\n" +
                "WHERE id = '" + id +"'\n" +
                "\tAND customer_id = '" + customerId + "'\n" +
                "    AND flat_id = '" + flatId + "'";
    }

    public static String contructQueryGetAll(int page, int size) {
        return "SELECT rent_contract.id, building.name AS building_name, room_number, customer.fullname AS customer_name, \n" +
                "\texpiry_date, rent_contract.status AS status, contract AS rent_contract_link, flat.id AS flat_id, customer.id AS customer_id\n" +
                "FROM building JOIN flat ON building.id=flat.building_id  \n" +
                "\tJOIN rent_contract ON rent_contract.flat_id=flat.id     \n" +
                "\tJOIN customer ON customer.id=rent_contract.customer_id \n" +
                "ORDER BY rent_contract.status DESC, building_name ASC, room_number ASC " +
                "LIMIT " + size + " OFFSET " + (page*size);
    }

    public static String contructQueryGetAllTotalRow() {
        return "SELECT count(rent_contract.id)\n" +
                "FROM building JOIN flat ON building.id=flat.building_id  \n" +
                "\tJOIN rent_contract ON rent_contract.flat_id=flat.id     \n" +
                "\tJOIN customer ON customer.id=rent_contract.customer_id \n";
    }
}
