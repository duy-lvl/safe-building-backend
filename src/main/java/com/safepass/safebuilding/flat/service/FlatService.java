package com.safepass.safebuilding.flat.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.common.meta.FlatStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.UUID;


public interface FlatService {
    ResponseEntity<ResponseObject> getFlatList(int page, int size) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException;

    ResponseEntity<ResponseObject> getAvailableFlatByBuilding(String buildingId) throws NoSuchDataException;

    void updateFlatStatus(UUID flatId, FlatStatus status) throws SQLException;

}
