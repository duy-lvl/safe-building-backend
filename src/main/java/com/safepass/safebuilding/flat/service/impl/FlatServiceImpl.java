package com.safepass.safebuilding.flat.service.impl;

import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.common.meta.FlatStatus;
import com.safepass.safebuilding.common.validation.PaginationValidation;
import com.safepass.safebuilding.flat.dto.AvailableFlatDTO;
import com.safepass.safebuilding.flat.dto.FlatDTO;
import com.safepass.safebuilding.flat.jdbc.FlatJDBC;
import com.safepass.safebuilding.flat.service.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class FlatServiceImpl implements FlatService {
    @Autowired
    private FlatJDBC flatJDBC;
    @Autowired
    private PaginationValidation paginationValidation;

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
    public ResponseEntity<ResponseObject> getAvailableFlatByBuilding(String buildingId) throws NoSuchDataException {
        String query = FlatServiceUtil.queryGetFlat(buildingId, FlatStatus.AVAILABLE);
        List<AvailableFlatDTO> flats = flatJDBC.getAvailableFlatByBuildingIdAndStatus(query);
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
}
