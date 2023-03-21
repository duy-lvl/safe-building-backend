package com.safepass.safebuilding.common.controller.bill.service;

import com.safepass.safebuilding.common.controller.bill.dto.BillCreate;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import org.springframework.http.ResponseEntity;

public interface BillService {
    ResponseEntity<ResponseObject> getBillList(String customerId, int page, int size)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException;

    ResponseEntity<ResponseObject> getBillList(int page, int size)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException;
    ResponseEntity<ResponseObject> createBill(BillCreate billCreate) throws NoSuchDataException;
}
