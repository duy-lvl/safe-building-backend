package com.safepass.safebuilding.dashboard.service.impl;

public class DashboardServiceUtils {
    public static String getContract(int year) {
        return "SELECT MONTH(start_date) AS month, count(id) AS quantity \n" +
                "FROM safe_building.rent_contract \n" +
                "WHERE YEAR(start_date) = " + year +
                " GROUP BY MONTH(start_date)\n" +
                "ORDER BY start_date ASC";
    }

    public static String getBill(int year) {
        return "SELECT MONTH(date) AS month, SUM(value) AS total\n" +
                "FROM safe_building.bill\n" +
                "WHERE YEAR(date) = " + year +
                "\tAND status = 'PAID' \n" +
                "GROUP BY MONTH(date)\n" +
                "ORDER BY date ASC";
    }

    public static String getContract(int year, int month) {
        return "SELECT MONTH(start_date) AS month, count(id) AS quantity \n" +
                "FROM safe_building.rent_contract \n" +
                "WHERE YEAR(start_date) = " + year +
                "\t AND MONTH(start_date) = " + month +
                " ORDER BY start_date ASC";
    }

    public static String getBill(int year, int month) {
        return "SELECT MONTH(date) AS month, SUM(value) AS total\n" +
                "FROM safe_building.bill\n" +
                "WHERE YEAR(date) = " + year +
                " AND MONTH(date) = " + month +
                "\t AND status = 'PAID' \n" +
                "GROUP BY MONTH(date)\n" +
                "ORDER BY date ASC";
    }

    public static String getService(int year, int month) {
        return "WITH t AS (\n" +
                "\tSELECT service.name, COALESCE(SUM(quantity),0) AS quantity, service.id,\n" +
                "\t\tservice.price * COALESCE(SUM(quantity),0) AS value, \n" +
                "\t\tCOALESCE(YEAR(date),0) AS year, COALESCE(MONTH(date),0) AS month\n" +
                "\tFROM service JOIN bill_item ON service.id = bill_item.service_id\n" +
                "\t\t JOIN bill ON bill.id = bill_item.bill_id\n" +
                "\tGROUP BY service.id, YEAR(date), MONTH(date)\n" +
                ")\n" +
                "SELECT service.name, COALESCE(t.quantity,0) AS quantity, COALESCE(t.value,0) AS value\n" +
                "FROM service LEFT JOIN t ON t.id = service.id\n" +
                "\tAND t.year = " + year + " AND t.month = " + month + "\n" +
                "ORDER BY quantity ASC";
    }

}
