package com.safepass.safebuilding.device.service.impl;

public class DeviceServiceUtil {

    public static String constructQueryGetAll(int page, int size) {
        return "SELECT device.customer_id, device.token, fullname\n" +
                "FROM device JOIN customer ON device.customer_id = customer.id \n" +
                "LIMIT " + size + " OFFSET " + (page * size);
    }

    public static String constructQueryGetTotalRow(int page, int size) {
        return "SELECT count(device.customer_id) \n" +
                "FROM device JOIN customer ON device.customer_id = customer.id \n" +
                "LIMIT " + size + " OFFSET " + (page * size);
    }
}
