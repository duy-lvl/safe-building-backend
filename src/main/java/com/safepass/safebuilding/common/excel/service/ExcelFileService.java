package com.safepass.safebuilding.common.excel.service;

import com.safepass.safebuilding.building.entity.Building;
import com.safepass.safebuilding.building.repository.BuildingRepository;
import com.safepass.safebuilding.common.excel.entity.Info;
import com.safepass.safebuilding.common.excel.utils.ExcelUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class ExcelFileService {
    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    ExcelUtils excelUtils;

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
                if (bill == false && !headers.contains(dataFormatter.formatCellValue(row.getCell(0)))) {
                    bill = true;
                }
                if (bill) {
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

    public void createFileForMonthlyBill(UUID buildingId) throws IOException {
        Building building = buildingRepository.findBuildingById(buildingId).orElse(null);
        if(building == null){
            return;
        }
        List<Info> infos = excelUtils.getListInfoForExcel(building.getId());
        Workbook workbook = null;
        FileOutputStream fileOutputStream = null;
        try{
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("MonthlyBill");
            String[] columnNamesBuilding = {"Building Id", "Building Name", "BuidlingAddress"};
            String[] columnNamesBill = {"Contract Id", "Customer Id", "Flat Id", "Flat room no", "Electric", "Water"};
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerFont.setColor(IndexedColors.BLACK.index);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.index);

            //first row
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("MonthlyFee");
            cell.setCellStyle(headerStyle);

            //second row - building data header
            row = sheet.createRow(1);
            for(int i = 0; i<columnNamesBuilding.length; i++){
                cell = row.createCell(i);
                cell.setCellValue(columnNamesBuilding[i]);
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(i);
            }

            //third row - building data
            row=sheet.createRow(2);
            row.createCell(0).setCellValue(String.valueOf(building.getId()));
            row.createCell(1).setCellValue((building.getName()));
            row.createCell(2).setCellValue((building.getAddress()));

            //forth row - flat, customer, contract, bill header
            row = sheet.createRow(3);
            for(int i = 0; i<columnNamesBill.length; i++){
                cell = row.createCell(i);
                cell.setCellValue(columnNamesBill[i]);
                cell.setCellStyle(headerStyle);
            }
            int currentRow = 4;
            //list data
            if(infos.size()>0){
                for(Info info : infos){
                    row = sheet.createRow(currentRow++);
                    row.createCell(0).setCellValue(String.valueOf(info.getContractId()));
                    row.createCell(1).setCellValue(String.valueOf(info.getCustomerId()));
                    row.createCell(2).setCellValue(String.valueOf(info.getFlatId()));
                    row.createCell(3).setCellValue((info.getRoomNumber()));
                }
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);

            fileOutputStream = new FileOutputStream("src/main/resources/MonthlyBill"+building.getId()+".xlsx");
            workbook.write(fileOutputStream);
        } catch(Exception e) {
            log.error(e.getMessage());
        }finally{
            if(fileOutputStream!=null){
                fileOutputStream.close();
            }
            if(workbook!=null){
                workbook.close();
            }
        }


    }
}
