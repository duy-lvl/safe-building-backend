package com.safepass.safebuilding.building.service.impl;

import com.safepass.safebuilding.building.dto.AvailableBuildingDTO;
import com.safepass.safebuilding.building.dto.BuildingDTO;
import com.safepass.safebuilding.building.entity.Building;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.building.repository.BuildingRepository;
import com.safepass.safebuilding.building.service.BuildingService;
import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.meta.BuildingStatus;
import com.safepass.safebuilding.common.utils.ModelMapperCustom;
import com.safepass.safebuilding.common.validation.PaginationValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;

    private ModelMapperCustom modelMapper = new ModelMapperCustom();
    @Autowired
    private PaginationValidation paginationValidation;

    @Override
    public ResponseEntity<ResponseObject> getBuildingList(int page, int size) {
        try {
            paginationValidation.validatePageSize(page, size);
            Pageable pageRequest = PageRequest.of(page - 1, size);
            Page<Building> buildingPage = buildingRepository.findAll(pageRequest);
            int totalPage = buildingPage.getTotalPages();
            Pagination pagination = new Pagination(page, size, totalPage);
            paginationValidation.validateMaxPageNumber(pagination);

            List<Building> buildings = buildingPage.getContent();
            List<BuildingDTO> buildingDTOs = modelMapper.mapList(buildings, BuildingDTO.class);

            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, buildingDTOs));
            return responseEntity;
        } catch (InvalidPageSizeException | MaxPageExceededException e) {
            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), e.getMessage(), null, null));
            return responseEntity;
        } catch (NoSuchDataException e) {
            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_FOUND.toString(), e.getMessage(), null, null));
            return responseEntity;
        }

//        return service.getAll(page, size, buildingRepository, BuildingDTO.class);
    }

    public ResponseEntity<ResponseObject> searchBuildingByName(String name, int page, int size) {
        try {
            paginationValidation.validatePageSize(page, size);
            Pageable pageRequest = PageRequest.of(page - 1, size);
            Page<Building> buildingPage = buildingRepository.findByNameContainsIgnoreCase(name, pageRequest);
            int totalPage = buildingPage.getTotalPages();
            Pagination pagination = new Pagination(page, size, totalPage);
            paginationValidation.validateMaxPageNumber(pagination);

            List<Building> buildings = buildingPage.getContent();
            List<BuildingDTO> buildingDTOs = modelMapper.mapList(buildings, BuildingDTO.class);

            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, buildingDTOs));
            return responseEntity;
        } catch (InvalidPageSizeException | MaxPageExceededException e) {
            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), e.getMessage(), null, null));
            return responseEntity;
        } catch (NoSuchDataException e) {
            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_FOUND.toString(), e.getMessage(), null, null));
            return responseEntity;
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getAvailableBuildings() {
        List<Building> buildings = buildingRepository.findByStatusOrderByNameAsc(BuildingStatus.AVAILABLE);
        ResponseEntity<ResponseObject> responseEntity;
        if (buildings.isEmpty()) {
            responseEntity  = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_FOUND.toString(), "No building available", null, null));
        } else {
            List<AvailableBuildingDTO> buildingDTOs = modelMapper.mapList(buildings, AvailableBuildingDTO.class);

            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, buildingDTOs));
        }

        return responseEntity;



    }
}
