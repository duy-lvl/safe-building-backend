package com.safepass.safebuilding.building.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import org.springframework.http.ResponseEntity;

public interface BuildingService {

    /**
     * Get building list
     *
     * @param page {@code int}
     * @param size {@code int}
     * @return ResponseEntity<ResponseObject>
     * @throws InvalidPageSizeException
     * @throws MaxPageExceededException
     * @throws NoSuchDataException
     */
    ResponseEntity<ResponseObject> getBuildingList(int page, int size)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException;

    /**
     * Search building by name
     *
     * @param name {@code String}
     * @param page {@code int}
     * @param size {@code int}
     *
     * @return ResponseEntity<ResponseObject>
     *
     * @throws InvalidPageSizeException
     * @throws MaxPageExceededException
     * @throws NoSuchDataException
     *
     */
    ResponseEntity<ResponseObject> searchBuildingByName(String name, int page, int size)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException;

    /**
     * Get all available buildings for create contract
     *
     * @return ResponseEntity<ResponseObject>
     *
     * @throws NoSuchDataException
     */

    public ResponseEntity<ResponseObject> getAvailableBuildings() throws NoSuchDataException;
}
