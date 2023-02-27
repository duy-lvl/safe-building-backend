package com.safepass.safebuilding.bill.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface BillService {
    ResponseEntity<ResponseObject> getBillList(int page, int size);
}
