package com.safepass.safebuilding.flat.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.common.meta.FlatStatus;
import com.safepass.safebuilding.flat.dto.RequestFlat;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.UUID;


public interface FlatService {

    /**
     * Get flat list
     *
     * @param page {@code int}
     * @param size {@code int}
     * @return ResponseEntity<ResponseObject>
     *
     * @throws InvalidPageSizeException
     * @throws MaxPageExceededException
     * @throws NoSuchDataException
     */
    ResponseEntity<ResponseObject> getFlatList(int page, int size)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException;

    /**
     * Get all available flats in a building by building id
     *
     * @param buildingId {@code String}
     * @return ResponseEntity<ResponseObject>
     *
     * @throws NoSuchDataException
     */
    ResponseEntity<ResponseObject> getAvailableFlatByBuilding(String buildingId) throws NoSuchDataException;

    /**
     * Update flat status
     *
     * @param flatId {@code UUID}
     * @param status {@code FlatStatus}
     * @return ResponseEntity<ResponseObject>
     *
     * @throws SQLException
     */
    void updateFlatStatus(UUID flatId, FlatStatus status) throws SQLException;

    /**
     * Create a flat
     *
     * @param requestFlat {@code RequestFlat}
     * @Return ResponseEntity<ResponseObject>
     */

    ResponseEntity<ResponseObject> createFlat(RequestFlat requestFlat) throws InvalidDataException;
}
