package com.safepass.safebuilding.common.excel.service;

import com.google.common.net.HttpHeaders;
import com.safepass.safebuilding.bill.entity.Bill;
import com.safepass.safebuilding.bill.repository.BillRepository;
import com.safepass.safebuilding.bill_item.entity.BillItem;
import com.safepass.safebuilding.bill_item.repository.BillItemRepository;
import com.safepass.safebuilding.building.entity.Building;
import com.safepass.safebuilding.building.repository.BuildingRepository;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.excel.entity.Info;
import com.safepass.safebuilding.common.excel.utils.ExcelUtils;
import com.safepass.safebuilding.common.firebase.service.IImageService;
import com.safepass.safebuilding.common.meta.BillStatus;
import com.safepass.safebuilding.rent_contract.entity.RentContract;
import com.safepass.safebuilding.rent_contract.repository.RentContractRepository;
import com.safepass.safebuilding.service.entity.Service;
import com.safepass.safebuilding.service.repository.ServiceRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@org.springframework.stereotype.Service
@Log4j2
public class ExcelFileService {
    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    ExcelUtils excelUtils;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private RentContractRepository rentContractRepository;
    @Autowired
    private BillItemRepository billItemRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private IImageService imageService;

    public ResponseEntity<ResponseObject> uploadFileForMonthlyBill(MultipartFile uploadFile) throws IOException {
        boolean checkBill = false;
        List<String> headers = new ArrayList<>();
//        headers.add("Monthly Fee");
//        headers.add("Building Id");
//        headers.add("Building Name");
//        headers.add("Building Address");
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

                if (!checkBill && headers.contains(dataFormatter.formatCellValue(row.getCell(0)))) {
                    checkBill = true;
                }
                if (checkBill) {
                    while (iterator.hasNext()) {
                        row = iterator.next();
                        UUID contractId = UUID.fromString(dataFormatter.formatCellValue(row.getCell(0)));
                        UUID customerId = UUID.fromString(dataFormatter.formatCellValue(row.getCell(1)));
                        UUID flatId = UUID.fromString(dataFormatter.formatCellValue(row.getCell(2)));
                        int electricBill = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(4)));
                        int waterBill = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(5)));

                        Optional<RentContract> rctemp = rentContractRepository.findRentContractById(contractId);

                        if (rctemp.isPresent()) {

                            RentContract rentContract = rctemp.get();

                            int billValue = rentContract.getValue();

                            Bill bill = new Bill(UUID.randomUUID(), rentContract, Date.valueOf(LocalDate.now()), billValue, BillStatus.UNPAID);
                            bill = billRepository.save(bill);

                            Service service = serviceRepository.findServiceByName("Electricity");
                            BillItem billItem = new BillItem(UUID.randomUUID(), bill, electricBill, electricBill , service);
                            billItemRepository.save(billItem);
                            billValue += electricBill * service.getPrice();


                            service = serviceRepository.findServiceByName("Water");
                            billItem = new BillItem(UUID.randomUUID(), bill, waterBill, waterBill , service);
                            billItemRepository.save(billItem);
                            billValue += waterBill * service.getPrice();

                            service = serviceRepository.findServiceByName("HAVC");
                            billItem = new BillItem(UUID.randomUUID(), bill, 1, 1 * service.getPrice(), service);
                            billItemRepository.save(billItem);
                            billValue += waterBill * service.getPrice();


                            service = serviceRepository.findServiceByName("Parking");
                            billItem = new BillItem(UUID.randomUUID(), bill, 1, 1 * service.getPrice(), service);
                            billItemRepository.save(billItem);
                            billValue += waterBill * service.getPrice();


                            service = serviceRepository.findServiceByName("Maintenance");
                            billItem = new BillItem(UUID.randomUUID(), bill, 1, 1 * service.getPrice(), service);
                            billItemRepository.save(billItem);
                            billValue += waterBill * service.getPrice();

                            bill.setValue(billValue);
                            bill = billRepository.save(bill);
                        /*service = serviceRepository.findServiceByName("Parking");
                        quantity = random(1,3);
                        billItem = new BillItem(UUID.randomUUID(), bill, quantity, quantity*service.getPrice(), service);
                        billItemRepository.save(billItem);
                        billValue += quantity* service.getPrice();

                        service = serviceRepository.findServiceByName("HVAC");
                        quantity = 1;
                        billItem = new BillItem(UUID.randomUUID(), bill, quantity, quantity*service.getPrice(), service);
                        billItemRepository.save(billItem);
                        billValue += quantity* service.getPrice();*/
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

                }
            }
            workbook.close();
            file.close();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));
    }

    public ResponseEntity<ResponseObject> createFileForMonthlyBill(UUID buildingId) throws IOException {
        Building building = buildingRepository.findBuildingById(buildingId).orElse(null);
        if (building == null) {
            return null;
        }
        List<Info> infos = excelUtils.getListInfoForExcel(building.getId());
        Workbook workbook = null;
        FileOutputStream fileOutputStream = null;
        try {
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("MonthlyBill");
            String[] columnNamesBuilding = {"Building Id", "Building Name", "BuidlingAddress"};
            String[] columnNamesBill = {"Contract Id", "Customer Id", "Flat Id", "Flat room no", "Electric (VND)", "Water (VND)"};
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
            for (int i = 0; i < columnNamesBuilding.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(columnNamesBuilding[i]);
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(i);
            }

            //third row - building data
            row = sheet.createRow(2);
            row.createCell(0).setCellValue(String.valueOf(building.getId()));
            row.createCell(1).setCellValue((building.getName()));
            row.createCell(2).setCellValue((building.getAddress()));

            //forth row - flat, customer, contract, bill header
            row = sheet.createRow(3);
            for (int i = 0; i < columnNamesBill.length; i++) {
                cell = row.createCell(i);
                cell.setCellValue(columnNamesBill[i]);
                cell.setCellStyle(headerStyle);
            }
            int currentRow = 4;
            //list data
            if (infos.size() > 0) {
                for (Info info : infos) {
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

            fileOutputStream = new FileOutputStream("src/main/resources/MonthlyBill" + building.getId() + ".xlsx");
            workbook.write(fileOutputStream);
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (workbook != null) {
                workbook.close();
            }

            Path path = Paths.get("src/main/resources/MonthlyBill" + buildingId + ".xlsx");
            byte[] data = Files.readAllBytes(path);
            File file = new File("src/main/resources/MonthlyBill" + buildingId + ".xlsx");

            String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            String url = imageService.save(file, data, contentType);
            if (file.exists()){
                file.delete();
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, url));
        }


    }
}
