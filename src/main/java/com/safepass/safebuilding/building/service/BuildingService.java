package com.safepass.safebuilding.building.service;

import com.safepass.safebuilding.building.dto.BuildingDTO;
import com.safepass.safebuilding.building.dto.BuildingPostRequest;
import com.safepass.safebuilding.building.dto.BuildingGetRequest;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.common.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

public interface BuildingService {
    /**
     * This method can be used to get building list which can take search key and can be sorted with pagination
     * @param buildingGetRequest
     * @return ResponseEntity<ResponseObject>
     */
    ResponseEntity<ResponseObject> getBuildingList(BuildingGetRequest buildingGetRequest)  throws ResourceNotFoundException;

    /**
     * This method can be used to get building list with pagination
     * @param page
     * @param size
     * @return ResponseEntity<ResponseObject>
     * @throws InvalidPageSizeException
     * @throws MaxPageExceededException
     * @throws NoSuchDataException
     */
    ResponseEntity<ResponseObject> getBuildingList(int page, int size) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException;

    /**
     * This method can be used to search building by their name
     * @param name
     * @param page
     * @param size
     * @return ResponseEntity<ResponseObject>
     * @throws InvalidPageSizeException
     * @throws MaxPageExceededException
     * @throws NoSuchDataException
     */
    ResponseEntity<ResponseObject> searchBuildingByName(String name, int page, int size) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException;

    ResponseEntity<ResponseObject> getAvailableBuildings() throws NoSuchDataException;

    /**
     * This method is used to create a new building
     * @param buildingPostRequest
     * @return ResponseEntity<ResponseObject>
     */
    ResponseEntity<ResponseObject> createBuilding(BuildingPostRequest buildingPostRequest);

    /**
     * This method is used to update a building
     * @param building, id
     * @return ResponseEntity<ResponseObject>
     */
    ResponseEntity<ResponseObject> updateBuilding(BuildingDTO buildingDTO);
}
