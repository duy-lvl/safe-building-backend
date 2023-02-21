package com.safepass.safebuilding.rent_contract.service.impl;

import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.common.firebase.service.IImageService;
import com.safepass.safebuilding.common.validation.PaginationValidation;
import com.safepass.safebuilding.rent_contract.dto.RentContractDTO;
import com.safepass.safebuilding.rent_contract.jdbc.RentContractJDBC;
import com.safepass.safebuilding.rent_contract.service.RentContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class RentContractServiceImpl implements RentContractService {
    @Autowired
    private IImageService imageService;
    @Autowired
    private RentContractJDBC rentContractJDBC;
    @Autowired
    private PaginationValidation paginationValidation;

    @Override
    public ResponseEntity<ResponseObject> uploadFile(MultipartFile[] files, String customerId, String rentContractId, String flatId) throws IOException {
        String url = create(files).split("/")[4];

        String query = RentContractServiceUtil.constructQuery(rentContractId, customerId, flatId, url);
        boolean result = rentContractJDBC.uploadRentContract(query);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Failed", null, null));
        }

    }

    public String create(MultipartFile[] files) throws IOException {

        String imageUrl = "";
        for (MultipartFile file : files) {
            String fileName = imageService.save(file);
            imageUrl = imageService.getImageUrl(fileName);
        }

        return imageUrl;
    }

    @Override
    public ResponseEntity<ResponseObject> getList(int page, int size) {
        try {
            paginationValidation.validatePageSize(page, size);


            String queryTotalRow = RentContractServiceUtil.contructQueryGetAllTotalRow();
            long totalRow = rentContractJDBC.getTotalRow(queryTotalRow);
            int totalPage = (int) Math.ceil(1.0 * totalRow / size);
            Pagination pagination = new Pagination(page, size, totalPage);
            paginationValidation.validateMaxPageNumber(pagination);
            String queryGetList = RentContractServiceUtil.contructQueryGetAll(page - 1, size);
            List<RentContractDTO> rentContracts = rentContractJDBC.getList(queryGetList);


            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, rentContracts));
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


}
