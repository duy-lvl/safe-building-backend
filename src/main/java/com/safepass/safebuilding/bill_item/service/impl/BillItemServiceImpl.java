package com.safepass.safebuilding.bill_item.service.impl;

import com.safepass.safebuilding.bill.dto.BillDTO;
import com.safepass.safebuilding.bill.entity.Bill;
import com.safepass.safebuilding.bill_item.entity.BillItem;
import com.safepass.safebuilding.bill_item.repository.BillItemRepository;
import com.safepass.safebuilding.bill_item.service.BillItemService;
import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.validation.PaginationValidation;
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
public class BillItemServiceImpl implements BillItemService {

    @Autowired
    private BillItemRepository billItemRepository;
//    @Override
//    public ResponseEntity<ResponseObject> getBillItem(String billId) {
//
//
//        List<BillItem> billPage = billItemRepository.findByBillId(UUID.fromString(billId));
//
//
//
//        ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.OK)
//                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, billDTOs));
//        return responseEntity;
//    }
}
