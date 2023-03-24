package com.safepass.safebuilding.common.excel.service;


import com.safepass.safebuilding.bill.entity.Bill;
import com.safepass.safebuilding.bill.repository.BillRepository;
import com.safepass.safebuilding.bill_item.entity.BillItem;
import com.safepass.safebuilding.bill_item.repository.BillItemRepository;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.meta.BillStatus;
import com.safepass.safebuilding.common.meta.RentContractStatus;
import com.safepass.safebuilding.rent_contract.entity.RentContract;
import com.safepass.safebuilding.rent_contract.repository.RentContractRepository;
import com.safepass.safebuilding.service.entity.Service;
import com.safepass.safebuilding.service.repository.ServiceRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@org.springframework.stereotype.Service
public class ExcelFileService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private RentContractRepository rentContractRepository;
    @Autowired
    private BillItemRepository billItemRepository;
    @Autowired
    private ServiceRepository serviceRepository;

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
//                    UUID contractId = UUID.fromString(dataFormatter.formatCellValue(row.getCell(1)));
//                    UUID customerId = UUID.fromString(dataFormatter.formatCellValue(row.getCell(2)));
                    while (iterator.hasNext()) {
                        row = iterator.next();

                        UUID flatId = UUID.fromString(dataFormatter.formatCellValue(row.getCell(2)));
                        int electricBill = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(4)));
                        int waterBill = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(5)));

                        Optional<RentContract> rctemp = rentContractRepository
                                .findRentContractByFlatIdAndStatus(
                                        flatId,
                                        RentContractStatus.VALID
                                );

                        if (rctemp.isPresent()) {

                            RentContract rentContract = rctemp.get();

                            int billValue = rentContract.getValue();

                            Bill bill = new Bill(UUID.randomUUID(),
                                    rentContract,
                                    Date.valueOf(LocalDate.now()),
                                    billValue,
                                    BillStatus.UNPAID
                            );
                            bill = billRepository.save(bill);

                            Service service = serviceRepository.findServiceByName("Electricity");
                            BillItem billItem = new BillItem(UUID.randomUUID(), bill, electricBill, electricBill * service.getPrice(), service);
                            billItemRepository.save(billItem);
                            billValue += electricBill * service.getPrice();


                            service = serviceRepository.findServiceByName("Water");
                            billItem = new BillItem(UUID.randomUUID(), bill, waterBill, waterBill * service.getPrice(), service);
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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));
    }
}
