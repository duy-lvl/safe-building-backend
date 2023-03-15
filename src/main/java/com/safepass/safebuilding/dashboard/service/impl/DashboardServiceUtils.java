package com.safepass.safebuilding.dashboard.service.impl;

public class DashboardServiceUtils {
    public static String getContract(int year) {
        return "SELECT start_date FROM safe_building.rent_contract \n" +
                "WHERE YEAR(start_date) = " + year +
                " ORDER BY start_date ASC";
    }
}
