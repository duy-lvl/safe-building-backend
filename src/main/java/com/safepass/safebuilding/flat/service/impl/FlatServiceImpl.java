package com.safepass.safebuilding.flat.service.impl;

import com.safepass.safebuilding.building.entity.Building;
import com.safepass.safebuilding.building.repository.BuildingRepository;
import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.common.meta.FlatStatus;
import com.safepass.safebuilding.common.validation.PaginationValidation;
import com.safepass.safebuilding.flat.dto.AvailableFlatDTO;
import com.safepass.safebuilding.flat.dto.FlatDTO;
import com.safepass.safebuilding.flat.dto.RequestFlat;
import com.safepass.safebuilding.flat.entity.Flat;
import com.safepass.safebuilding.flat.jdbc.FlatJDBC;
import com.safepass.safebuilding.flat.repository.FlatRepository;
import com.safepass.safebuilding.flat.service.FlatService;
import com.safepass.safebuilding.flat.validation.FlatValidation;
import com.safepass.safebuilding.flat_type.entity.FlatType;
import com.safepass.safebuilding.flat_type.repository.FlatTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FlatServiceImpl implements FlatService {
    @Autowired
    private FlatJDBC flatJDBC;
    @Autowired
    private PaginationValidation paginationValidation;

    @Autowired
    private FlatValidation flatValidation;
    @Autowired
    private FlatRepository flatRepository;

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public ResponseEntity<ResponseObject> getFlatList(int page, int size)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException
    {
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
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public ResponseEntity<ResponseObject> getFlatByBuilding(String buildingId) throws NoSuchDataException {
        String query = FlatServiceUtil.queryGetFlat(buildingId);
        List<AvailableFlatDTO> flats = flatJDBC.getFlatByBuildingId(query);
        ResponseEntity<ResponseObject> responseEntity;
        if (flats.isEmpty()) {
            throw new NoSuchDataException("No building found");
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, flats));
        }
        return responseEntity;

    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public void updateFlatStatus(UUID flatId, FlatStatus status) throws SQLException {

        String queryUpdateFlatStatus = FlatServiceUtil.queryUpdateStatus(flatId, status);
        boolean checkUpdate = flatJDBC.updateStatus(queryUpdateFlatStatus);
        if (!checkUpdate) {
            throw new SQLException("Flat does not exist");
        }
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public ResponseEntity<ResponseObject> createFlat(RequestFlat requestFlat) throws InvalidDataException {
        Flat flat = flatValidation.validateCreate(requestFlat);
        flatRepository.save(flat);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));

    }
}
