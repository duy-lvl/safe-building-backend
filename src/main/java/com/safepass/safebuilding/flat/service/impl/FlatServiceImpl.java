package com.safepass.safebuilding.flat.service.impl;

import com.safepass.safebuilding.building.dto.AvailableBuildingDTO;
import com.safepass.safebuilding.building.entity.Building;
import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.common.meta.BuildingStatus;
import com.safepass.safebuilding.common.meta.FlatStatus;
import com.safepass.safebuilding.common.utils.ModelMapperCustom;
import com.safepass.safebuilding.common.validation.PaginationValidation;
import com.safepass.safebuilding.flat.dto.AvailableFlatDTO;
import com.safepass.safebuilding.flat.dto.FlatDTO;
import com.safepass.safebuilding.flat.entity.Flat;
import com.safepass.safebuilding.flat.jdbc.FlatJDBC;
import com.safepass.safebuilding.flat.repository.FlatRepository;
import com.safepass.safebuilding.flat.service.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FlatServiceImpl implements FlatService {
    @Autowired
    private FlatJDBC flatJDBC;
    @Autowired
    private PaginationValidation paginationValidation;
    @Autowired
    private FlatRepository flatRepository;
    private ModelMapperCustom modelMapper = new ModelMapperCustom();
    @Override
    public ResponseEntity<ResponseObject> getFlatList(int page, int size){
        try {

            paginationValidation.validatePageSize(page, size);
            String totalRowQuery = FlatServiceUtil.construcQueryForTotalRow();
            Long totalRow = flatJDBC.getTotalRow(totalRowQuery);
            int totalPage = (int) Math.ceil(1.0 * totalRow / size);
            Pagination pagination = new Pagination(page, size, totalPage);
            paginationValidation.validateMaxPageNumber(pagination);

            String query = FlatServiceUtil.constructQueryForGetFlatList(page - 1, size);
            List<FlatDTO> flatDTOs = flatJDBC.getFlatList(query);

            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, flatDTOs));
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
    public ResponseEntity<ResponseObject> getAvailableFlatByBuilding(String buildingId) {
        String query = FlatServiceUtil.queryGetFlat(buildingId, FlatStatus.AVAILABLE);
        List<AvailableFlatDTO> flats = flatJDBC.getAvailableFlatByBuildingIdAndStatus(query);
        ResponseEntity<ResponseObject> responseEntity;
        if (flats.isEmpty()) {
            responseEntity  = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_FOUND.toString(), "No building available", null, null));
        } else {


            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, flats));
        }

        return responseEntity;

    }
}
