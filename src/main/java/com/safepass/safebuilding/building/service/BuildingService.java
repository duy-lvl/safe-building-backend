package com.safepass.safebuilding.building.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface BuildingService {
    ResponseEntity<ResponseObject> getBuildingList(int page, int size);
    ResponseEntity<ResponseObject> searchBuildingByName(String name, int page, int size);
}
