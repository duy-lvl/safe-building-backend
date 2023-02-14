package com.safepass.safebuilding.rent_contract.service.impl;

public class RentContractServiceUtil {

    public static String constructQuery(String id, String customerId, String flatId, String url) {
        return "UPDATE rent_contract\n" +
                "SET contract = '" + url +"'\n" +
                "WHERE id = '" + id +"'\n" +
                "\tAND customer_id = '" + customerId + "'\n" +
                "    AND flat_id = '" + flatId + "'";
    }
}
