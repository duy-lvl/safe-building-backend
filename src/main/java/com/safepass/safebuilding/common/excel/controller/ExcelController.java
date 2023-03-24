package com.safepass.safebuilding.common.excel.controller;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.excel.service.ExcelFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ExcelController {
    @Autowired
    ExcelFileService excelFileService;

    @PostMapping("/upload-excel-file")
    public ResponseEntity<ResponseObject> uploadExcelFile(@RequestParam("file") MultipartFile uploadFile) throws IOException {
        excelFileService.uploadFileForMonthlyBill(uploadFile);
        return null;
    }
}
