package com.safepass.safebuilding.bill.service.impl;

import com.safepass.safebuilding.admin.entity.Admin;
import com.safepass.safebuilding.bill.dto.BillDTO;
import com.safepass.safebuilding.bill.entity.Bill;
import com.safepass.safebuilding.bill.repository.BillRepository;
import com.safepass.safebuilding.bill.service.BillService;
import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.common.utils.ModelMapperCustom;
import com.safepass.safebuilding.common.validation.PaginationValidation;
import com.safepass.safebuilding.customer.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private PaginationValidation paginationValidation;
    @Autowired
    private BillRepository billRepository;
    private ModelMapperCustom modelMapperCustom = new ModelMapperCustom();
    @Override
    public ResponseEntity<ResponseObject> getBillList(String customerId, int page, int size)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException
    {
            paginationValidation.validatePageSize(page, size);
            Pageable pageable = PageRequest.of(page-1, size);
            Page<Bill> billPage = billRepository.findAll(pageable);
            int totalPage = billPage.getTotalPages();
            Pagination pagination = new Pagination(page, size, totalPage);
            paginationValidation.validateMaxPageNumber(pagination);
            List<Bill> bills = billPage.getContent();
            List<BillDTO> billDTOs = modelMapperCustom.mapList(bills, BillDTO.class);

            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, billDTOs));
            return responseEntity;

    }
}
