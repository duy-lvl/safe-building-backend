package com.safepass.safebuilding.flat.service.impl;

public class FlatServiceUtil {

    public static String constructQueryForGetFlatList(int page, int size) {
        return "SELECT flat.id, building.name AS building_name, room_number, flat_type.name AS flat_type, price, flat.status\n" +
                "FROM flat JOIN building ON flat.building_id = building.id\n" +
                "\tJOIN flat_type ON flat.flat_type_id = flat_type.id \n" +
                "LIMIT " + size + " OFFSET " + (page * size);
    }

    public static String construcQueryForTotalRow() {
        return "SELECT count(room_number)\n" +
                "FROM flat JOIN building ON flat.building_id = building.id\n" +
                "\tJOIN flat_type ON flat.flat_type_id = flat_type.id";
    }
}
