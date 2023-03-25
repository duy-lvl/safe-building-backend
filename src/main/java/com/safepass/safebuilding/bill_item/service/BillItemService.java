package com.safepass.safebuilding.bill_item.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface BillItemService {
    ResponseEntity<ResponseObject> getBillItem(String billId);
}
