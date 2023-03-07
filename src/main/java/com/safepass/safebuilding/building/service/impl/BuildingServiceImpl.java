package com.safepass.safebuilding.building.service.impl;

import com.safepass.safebuilding.building.dto.AvailableBuildingDTO;
import com.safepass.safebuilding.building.dto.BuildingDTO;
import com.safepass.safebuilding.building.entity.Building;
import com.safepass.safebuilding.building.dto.BuildingPostRequest;
import com.safepass.safebuilding.building.dto.BuildingGetRequest;
import com.safepass.safebuilding.building.repository.BuildingRepository;
import com.safepass.safebuilding.building.service.BuildingService;
import com.safepass.safebuilding.building.utils.BuildingUtils;
import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.common.exception.ResourceNotFoundException;
import com.safepass.safebuilding.common.meta.BuildingStatus;
import com.safepass.safebuilding.common.utils.ModelMapperCustom;
import com.safepass.safebuilding.common.validation.PaginationValidation;
import com.safepass.safebuilding.flat.repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BuildingServiceImpl implements BuildingService {
    private final ModelMapperCustom modelMapper = new ModelMapperCustom();
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private FlatRepository flatRepository;
    @Autowired
    private BuildingUtils buildingUtils;
    @Autowired
    private PaginationValidation paginationValidation;

    @Override
    public ResponseEntity<ResponseObject> getBuildingList(int page, int size) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException {
        try {
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_FOUND.toString(), e.getMessage(), null, null));
        }

//        return service.getAll(page, size, buildingRepository, BuildingDTO.class);
    }

    @Override
    public ResponseEntity<ResponseObject> searchBuildingByName(String name, int page, int size) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException {
        try {
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

    @Override
    public ResponseEntity<ResponseObject> getBuildingList(BuildingGetRequest buildingGetRequest) throws ResourceNotFoundException {
        try {
            String query = buildingUtils.COUNT_RECORD_QUERY +
                    buildingUtils.appendSearchQuery(buildingGetRequest.getSearchKey());
            buildingUtils.appendSortQuery(buildingGetRequest);
            paginationValidation.validatePageSize(buildingGetRequest.getPage(), buildingGetRequest.getSize());
            int totalRow = buildingUtils.getTotalRow(query);
            int totalPage = (int) Math.ceil(1.0 * totalRow / buildingGetRequest.getSize());
            Pagination pagination = new Pagination(buildingGetRequest.getPage(), buildingGetRequest.getSize(), totalPage);
            paginationValidation.validateMaxPageNumber(pagination);

            query = buildingUtils.SELECT_BUILDINGS_QUERY +
                    buildingUtils.appendSearchQuery(buildingGetRequest.getSearchKey()) +
                    buildingUtils.appendSortQuery(buildingGetRequest) +
                    buildingUtils.appendPagination(buildingGetRequest.getPage() - 1, buildingGetRequest.getSize());
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

    @Override
    public ResponseEntity<ResponseObject> createBuilding(BuildingPostRequest buildingPostRequest) {
        Building building = buildingRepository.findBuildingByNameAndAddress(buildingPostRequest.getName(), buildingPostRequest.getAddress());
        ResponseObject responseObject;
        if(building != null){
            responseObject = new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Building is already exist.", null, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
        if(buildingPostRequest.getCapacity()<=0){
            buildingPostRequest.setCapacity(-1);
        }
        UUID uuid = UUID.randomUUID();
        building = new Building(uuid, buildingPostRequest.getName(), buildingPostRequest.getAddress(), buildingPostRequest.getStatus(), buildingPostRequest.getCapacity());
        buildingRepository.save(building);
        building = buildingRepository.findBuildingByNameAndAddress(building.getName(), building.getAddress());
        if(building != null) {
            BuildingDTO buildingDTO = modelMapper.map(building, BuildingDTO.class) ;
            responseObject = new ResponseObject(HttpStatus.OK.toString(), "New building is saved successfully.", null, buildingDTO );
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }  else {
            responseObject = new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Fail to save new building.", null, null );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObject);
        }

    }

    @Override
    public ResponseEntity<ResponseObject> updateBuilding(BuildingDTO buildingDTO) {
        Building building = buildingRepository.findById(UUID.fromString(buildingDTO.getId())).orElse(null);
        ResponseObject responseObject;
        if(building == null){
            responseObject = new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Building is not exist.", null, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }

        if(buildingRepository.findBuildingByNameAndAddressAndIdIsNotLike(buildingDTO.getName(), buildingDTO.getAddress(), UUID.fromString(buildingDTO.getId())) != null){
            responseObject = new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Duplicate building in the same address.", null, null );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
        int flatCount = flatRepository.findFlatByBuildingId(UUID.fromString(buildingDTO.getId())).size();
        if(buildingDTO.getCapacity()>0  && buildingDTO.getCapacity() < flatCount){
            responseObject = new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Capacity can not be smaller than the numbers of flats in the building.", null, null );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        } else if (buildingDTO.getCapacity()<=0){
            buildingDTO.setCapacity(-1);
        }

        building = modelMapper.map(buildingDTO, Building.class);
        building.setId(UUID.fromString(buildingDTO.getId()));
        building.setCapacity(buildingDTO.getCapacity());
        buildingRepository.save(building);

        if(building.toString().equals(buildingRepository.findById(building.getId()).orElse(null).toString())){
            buildingDTO = modelMapper.map(building, BuildingDTO.class) ;
            responseObject = new ResponseObject(HttpStatus.OK.toString(), "Building is updated successfully.", null, buildingDTO );
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }  else {
            responseObject = new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Fail to update building.", null, null );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObject);
        }
    }
}
