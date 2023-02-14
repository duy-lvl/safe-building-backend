package com.safepass.safebuilding.common.firebase.controller;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.firebase.service.IImageService;
import com.safepass.safebuilding.rent_contract.exception.MaxSizeUploadExceededException;
import com.safepass.safebuilding.rent_contract.service.RentContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/file-upload")
public class ImageController {
    @Autowired
    private RentContractService  rentContractService;

    @PostMapping
    public ResponseEntity<ResponseObject> uploadFile
            (
                    @RequestParam(name = "file") MultipartFile[] files,
                    @RequestParam(name = "customerId") String customerId,
                    @RequestParam(name = "flatId") String flatId,
                    @RequestParam(name = "rentContractId") String rentContractId
            ) throws IOException {
        return rentContractService.uploadFile(files, customerId, rentContractId, flatId);
    }
}
