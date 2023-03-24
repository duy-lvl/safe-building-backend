package com.safepass.safebuilding.common.excel.controller;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.excel.service.ExcelFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class ExcelController {
    @Autowired
    ExcelFileService excelFileService;

    @PostMapping("/rent-contract/upload-excel-file")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> uploadExcelFile(@RequestBody MultipartFile uploadFile) throws IOException {
        return excelFileService.uploadFileForMonthlyBill(uploadFile);

    }
}
