package com.safepass.safebuilding.common.controller.bill.utils;

public class BillUtils {

    public static String getBill(String customerId) {
        return "SELECT bill.id, bill.date, bill.status, bill.value\n" +
                "FROM customer JOIN rent_contract on customer.id = rent_contract.customer_id\n" +
                "\tJOIN bill on bill.rent_contract_id = rent_contract.id\n" +
                "WHERE customer.id =  '" + customerId +
                "' ORDER BY bill.date DESC";
    }

    public static String getTotalRow(String customerId) {
        return "SELECT count(bill.id)" +
                "FROM customer JOIN rent_contract on customer.id = rent_contract.customer_id\n" +
                "\tJOIN bill on bill.rent_contract_id = rent_contract.id\n" +
                "WHERE customer.id =  '" + customerId +
                "' ORDER BY bill.date DESC";
    }
}
