package com.safepass.safebuilding.building.service.impl;

import com.safepass.safebuilding.building.dto.AvailableBuildingDTO;
import com.safepass.safebuilding.building.dto.BuildingDTO;
import com.safepass.safebuilding.building.entity.BuildingRequest;
import com.safepass.safebuilding.building.entity.Building;
import com.safepass.safebuilding.building.utils.BuildingUtils;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.building.repository.BuildingRepository;
import com.safepass.safebuilding.building.service.BuildingService;
import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.meta.BuildingStatus;
import com.safepass.safebuilding.common.exception.ResourceNotFoundException;
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
    @Autowired
    private BuildingUtils buildingUtils;
    private ModelMapperCustom modelMapper = new ModelMapperCustom();
    @Autowired
    private PaginationValidation paginationValidation;

    @Override
    public ResponseEntity<ResponseObject> getBuildingList(int page, int size) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException {
        try{
            paginationValidation.validatePageSize(page, size);
            Pageable pageRequest = PageRequest.of(page - 1, size);
            Page<Building> buildingPage = buildingRepository.findAll(pageRequest);
            int totalPage = buildingPage.getTotalPages();
            Pagination pagination = new Pagination(page, size, totalPage);
            paginationValidation.validateMaxPageNumber(pagination);

            List<Building> buildings = buildingPage.getContent();
            List<BuildingDTO> buildingDTOs = modelMapper.mapList(buildings, BuildingDTO.class);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, buildingDTOs));
        } catch (InvalidPageSizeException | MaxPageExceededException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), e.getMessage(), null, null));
        } catch (NoSuchDataException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_FOUND.toString(), e.getMessage(), null, null));
        }

//        return service.getAll(page, size, buildingRepository, BuildingDTO.class);
    }

    public ResponseEntity<ResponseObject> searchBuildingByName(String name, int page, int size) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException {
        try{
            paginationValidation.validatePageSize(page, size);
            Pageable pageRequest = PageRequest.of(page - 1, size);
            Page<Building> buildingPage = buildingRepository.findByNameContainsIgnoreCase(name, pageRequest);
            int totalPage = buildingPage.getTotalPages();
            Pagination pagination = new Pagination(page, size, totalPage);
            paginationValidation.validateMaxPageNumber(pagination);

            List<Building> buildings = buildingPage.getContent();
            List<BuildingDTO> buildingDTOs = modelMapper.mapList(buildings, BuildingDTO.class);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, buildingDTOs));
        } catch (InvalidPageSizeException | MaxPageExceededException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), e.getMessage(), null, null));
        } catch (NoSuchDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_FOUND.toString(), e.getMessage(), null, null));
        }
    }
    public ResponseEntity<ResponseObject> getBuildingList(BuildingRequest buildingRequest) throws ResourceNotFoundException {
        try {
            String query = buildingUtils.COUNT_RECORD_QUERY +
                    buildingUtils.appendSearchQuery(buildingRequest.getSearchKey()) ;
                    buildingUtils.appendSortQuery(buildingRequest);
            paginationValidation.validatePageSize(buildingRequest.getPage(), buildingRequest.getSize());
            int totalRow = buildingUtils.getTotalRow(query);
            int totalPage = (int) Math.ceil(1.0 * totalRow / buildingRequest.getSize());
            Pagination pagination = new Pagination(buildingRequest.getPage(),  buildingRequest.getSize(), totalPage);
            paginationValidation.validateMaxPageNumber(pagination);

            query = buildingUtils.SELECT_BUILDINGS_QUERY +
                    buildingUtils.appendSearchQuery(buildingRequest.getSearchKey()) +
                    buildingUtils.appendSortQuery(buildingRequest) +
                    buildingUtils.appendPagination(buildingRequest.getPage() - 1, buildingRequest.getSize());
            List<Building> buildings = buildingUtils.getBuildingList(query);
            List<BuildingDTO> buildingDTOs = modelMapper.mapList(buildings, BuildingDTO.class);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, buildingDTOs));
        } catch (InvalidPageSizeException | MaxPageExceededException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), e.getMessage(), null, null));
        } catch (NoSuchDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_FOUND.toString(), e.getMessage(), null, null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getAvailableBuildings() throws NoSuchDataException {
        List<Building> buildings = buildingRepository.findByStatusOrderByNameAsc(BuildingStatus.AVAILABLE);
        ResponseEntity<ResponseObject> responseEntity;
        if (buildings.isEmpty()) {
           throw new NoSuchDataException("No building found");
        } else {
            List<AvailableBuildingDTO> buildingDTOs = modelMapper.mapList(buildings, AvailableBuildingDTO.class);

            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, buildingDTOs));
        }

        return responseEntity;
    }
}
