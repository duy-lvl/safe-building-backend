package com.safepass.safebuilding.common.excel.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class ExcelFileService {
    public void uploadFileForMonthlyBill(MultipartFile uploadFile) throws IOException {
        boolean bill = false;
        List<String> headers = new ArrayList<>();
        headers.add("Monthly Fee");
        headers.add("Building Id");
        headers.add("Building Name");
        headers.add("Building Address");
        headers.add("Contract Id");
        headers.add("Customer Id");
        headers.add("Flat Id");
        headers.add("Flat room no");
        headers.add("Electric");
        headers.add("Water");
        String filename = uploadFile.getOriginalFilename();
        String extension = null;
        if (filename != null) {
            extension = filename.substring(filename.lastIndexOf(".") + 1);
        }
        if (extension != null && "xlsx".equals(extension)) {
            InputStream file = uploadFile.getInputStream();
            Workbook workbook = new XSSFWorkbook(file);
            DataFormatter dataFormatter = new DataFormatter();
//            Iterator<Sheet> sheets = workbook.sheetIterator();
//            while(sheets.hasNext()){
//                Sheet sheet = sheets.next();
            Sheet sheet = workbook.getSheet("MonthlyBill");
            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row row = iterator.next();
                if(bill == false && !headers.contains(dataFormatter.formatCellValue(row.getCell(0)))){
                    bill = true;
                }
                if(bill){
                    UUID contractId = UUID.fromString(dataFormatter.formatCellValue(row.getCell(1)));
                    UUID customerId = UUID.fromString(dataFormatter.formatCellValue(row.getCell(2)));
                    UUID flatId = UUID.fromString(dataFormatter.formatCellValue(row.getCell(3)));
                    float electricBill = Float.parseFloat(dataFormatter.formatCellValue(row.getCell(4)));
                    float waterBill = Float.parseFloat(dataFormatter.formatCellValue(row.getCell(5)));
                }
//                Iterator<Cell> cellIterator = row.iterator();
//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
//                    String cellValue = dataFormatter.formatCellValue(cell);
//                    if(headers.contains(cellValue)){
//                        bill = false;
//                    } else {
//                        bill = true;
//                        System.out.print(cellValue + ",");
//                    }
//                }
            }
//            }
            workbook.close();
            file.close();
        }
    }
}
