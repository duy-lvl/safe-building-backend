package com.safepass.safebuilding.dashboard.service.impl;

public class DashboardServiceUtils {
    public static String getContract(int year) {
        return "SELECT start_date FROM safe_building.rent_contract \n" +
                "WHERE YEAR(start_date) = " + year +
                " ORDER BY start_date ASC";
    }

    public static String getBill(int year) {
        return "SELECT value, date FROM safe_building.bill \n" +
                "WHERE YEAR(date) = " + year +
                " AND status = 'PAID' " +
                " ORDER BY date ASC";
    }

    public static String getContract(int year, int month) {
        return "SELECT start_date FROM safe_building.rent_contract \n" +
                "WHERE YEAR(start_date) = " + year +
                " AND MONTH(start_date) = " + month +
                " ORDER BY start_date ASC";
    }

    public static String getBill(int year, int month) {
        return "SELECT value, date FROM safe_building.bill \n" +
                "WHERE YEAR(date) = " + year +
                " AND MONTH(date) = " + month +
                " AND status = 'PAID' " +
                " ORDER BY date ASC";
    }
}
