package com.safepass.safebuilding.building.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import org.springframework.http.ResponseEntity;

public interface BuildingService {
    ResponseEntity<ResponseObject> getBuildingList(int page, int size);
    ResponseEntity<ResponseObject> fetchBuildingByName(String name, int page, int size);
}
