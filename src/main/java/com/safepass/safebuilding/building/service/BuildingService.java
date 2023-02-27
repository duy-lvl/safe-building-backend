package com.safepass.safebuilding.building.service;

import com.safepass.safebuilding.building.entity.BuildingRequest;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import org.springframework.http.ResponseEntity;

public interface BuildingService {
    ResponseEntity<ResponseObject> getBuildingList(BuildingRequest buildingRequest);
    ResponseEntity<ResponseObject> getBuildingList(int page, int size) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException;
    ResponseEntity<ResponseObject> searchBuildingByName(String name, int page, int size) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException;
    public ResponseEntity<ResponseObject> getAvailableBuildings() throws NoSuchDataException;
}
