package com.safepass.safebuilding.bill.service.impl;

import com.safepass.safebuilding.admin.entity.Admin;
import com.safepass.safebuilding.bill.dto.BillDTO;
import com.safepass.safebuilding.bill.entity.Bill;
import com.safepass.safebuilding.bill.jdbc.BillJDBC;
import com.safepass.safebuilding.bill.repository.BillRepository;
import com.safepass.safebuilding.bill.service.BillService;
import com.safepass.safebuilding.bill.utils.BillUtils;
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
    @Autowired
    private BillJDBC billJDBC;
    private ModelMapperCustom modelMapperCustom = new ModelMapperCustom();
    @Override
    public ResponseEntity<ResponseObject> getBillList(String customerId, int page, int size)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException
    {
            paginationValidation.validatePageSize(page, size);
            String queryTotalRow = BillUtils.getTotalRow(customerId);
            long totalRow = billJDBC.getTotalRow(queryTotalRow);
            int totalPage = (int) Math.ceil(1.0 * totalRow / size);

            Pagination pagination = new Pagination(page, size, totalPage);
            paginationValidation.validateMaxPageNumber(pagination);
            String queryGetBill = BillUtils.getBill(customerId);
            List<BillDTO> billDTOs = billJDBC.getBill(queryGetBill);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, billDTOs));
    }
}
