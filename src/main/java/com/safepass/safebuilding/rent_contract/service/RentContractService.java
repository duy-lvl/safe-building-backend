package com.safepass.safebuilding.rent_contract.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RentContractService {

    ResponseEntity<ResponseObject> uploadFile(MultipartFile[] files, String customerId, String rentContractId, String flatId) throws IOException;
    String create(MultipartFile[] files) throws IOException;

    ResponseEntity<ResponseObject> getList(int page, int size);
}
