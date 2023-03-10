package com.safepass.safebuilding.rent_contract.service.impl;

import com.safepass.safebuilding.common.meta.RentContractStatus;
import com.safepass.safebuilding.rent_contract.dto.RequestObjectForCreate;
import com.safepass.safebuilding.rent_contract.dto.RequestObjectForUpdate;

import java.util.UUID;

public class RentContractServiceUtil {

    public static String constructQuery(String id, String customerId, String flatId, String url) {
        return "UPDATE rent_contract\n" +
                "SET contract = '" + url +"'\n" +
                "WHERE id = '" + id +"'\n" +
                "\tAND customer_id = '" + customerId + "'\n" +
                "    AND flat_id = '" + flatId + "'";
    }

    public static String contructQueryGetAll(int page, int size) {
        return "SELECT rent_contract.id, building.name AS building_name, room_number, customer.fullname AS customer_name, rent_contract.title, \n" +
                "\texpiry_date, rent_contract.status AS status, contract AS rent_contract_link, flat.id AS flat_id, customer.id AS customer_id," +
                "\t building.address, building.id AS building_id, rent_contract.start_date \n" +
                "FROM building JOIN flat ON building.id=flat.building_id  \n" +
                "\tJOIN rent_contract ON rent_contract.flat_id=flat.id     \n" +
                "\tJOIN customer ON customer.id=rent_contract.customer_id \n" +
                "ORDER BY rent_contract.status DESC, building_name ASC, room_number ASC " +
                "LIMIT " + size + " OFFSET " + (page*size);
    }

    public static String contructQueryGetById(String id) {
        return "SELECT rent_contract.id, building.name AS building_name, room_number, customer.fullname AS customer_name, rent_contract.title, \n" +
                "\texpiry_date, rent_contract.status AS status, contract AS rent_contract_link, flat.id AS flat_id, customer.id AS customer_id," +
                "\t building.id AS building_id, building.address, rent_contract.start_date \n" +
                "FROM building JOIN flat ON building.id=flat.building_id  \n" +
                "\tJOIN rent_contract ON rent_contract.flat_id=flat.id     \n" +
                "\tJOIN customer ON customer.id=rent_contract.customer_id \n" +
                "WHERE rent_contract.id = '" + id + "'";
    }

    public static String contructQueryGetAllTotalRow() {
        return "SELECT count(rent_contract.id)\n" +
                "FROM building JOIN flat ON building.id=flat.building_id  \n" +
                "\tJOIN rent_contract ON rent_contract.flat_id=flat.id     \n" +
                "\tJOIN customer ON customer.id=rent_contract.customer_id \n";
    }

    public static String queryInsert(RequestObjectForCreate requestObj, String url) {
        return "INSERT INTO rent_contract(id, title, contract, expiry_date, status, value, customer_id, flat_id)\n" +
                "VALUES('" + UUID.randomUUID() + "', '" + requestObj.getTitle() + "', '" + url + "', '" +
                requestObj.getExpiryDate() + "', '" + RentContractStatus.VALID + "', " + requestObj.getValue() +
                ", '" + requestObj.getCustomerId() + "', '" + requestObj.getFlatId() +"')";
    }

    public static String queryUpdate(RequestObjectForUpdate requestObj, String url) {
        String query = "UPDATE rent_contract SET title='" +requestObj.getTitle() +
                "', value=" +requestObj.getValue() +
                ", customer_id='" + requestObj.getCustomerId() +
                "', flat_id='" + requestObj.getFlatId() +
                "', expiry_date='" + requestObj.getExpiryDate();
        if (requestObj.isChange()) {
            query += "', contract='" + url;
        }
        query += "' WHERE id='" + requestObj.getContractId() + "'";
        return query;
    }
}
