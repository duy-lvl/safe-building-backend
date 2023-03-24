package com.safepass.safebuilding.common.excel.controller;

import com.google.common.net.HttpHeaders;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.excel.service.ExcelFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
public class ExcelController {
    @Autowired
    ExcelFileService excelFileService;

    @PostMapping("/upload-excel-file")
    public ResponseEntity<ResponseObject> uploadExcelFile(@RequestParam("file") MultipartFile uploadFile) throws IOException {
        excelFileService.uploadFileForMonthlyBill(uploadFile);
        return null;
    }

    @GetMapping("get-template-excel-file")
    public ResponseEntity<byte[]> downloadTemplateFile(@RequestParam UUID buildingId) throws IOException {
        excelFileService.createFileForMonthlyBill(buildingId);
        Path path = Paths.get("src/main/resources/MonthlyBill"+buildingId+".xlsx");
        byte[] data = Files.readAllBytes(path);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(String.valueOf(com.google.common.net.MediaType.MICROSOFT_EXCEL)))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName=\"MonthlyBill.xlsx"+"\"").body(data);
    }
}
