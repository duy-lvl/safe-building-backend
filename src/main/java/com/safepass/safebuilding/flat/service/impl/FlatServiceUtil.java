package com.safepass.safebuilding.flat.service.impl;

import com.safepass.safebuilding.common.meta.FlatStatus;

import java.util.UUID;

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

    public static String queryUpdateStatus(UUID id, FlatStatus status) {
        return "UPDATE flat SET status = '" + status + "' WHERE id = '" + id.toString() + "'";
    }

    public static String queryGetFlat(String buildingId) {
        return "SELECT id, room_number, price FROM flat WHERE building_id='" + buildingId
                + "' ORDER BY room_number ASC";
    }
}
