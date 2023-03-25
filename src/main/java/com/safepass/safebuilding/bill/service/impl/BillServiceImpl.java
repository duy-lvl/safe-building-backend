package com.safepass.safebuilding.bill.service.impl;

import com.safepass.safebuilding.bill.dto.BillDTO;
import com.safepass.safebuilding.bill.repository.BillRepository;
import com.safepass.safebuilding.bill.dto.BillCreate;
import com.safepass.safebuilding.bill.dto.ServiceDTO;
import com.safepass.safebuilding.bill.entity.Bill;
import com.safepass.safebuilding.bill.jdbc.BillJDBC;
import com.safepass.safebuilding.bill.service.BillService;
import com.safepass.safebuilding.bill.utils.BillUtils;
import com.safepass.safebuilding.bill_item.entity.BillItem;
import com.safepass.safebuilding.bill_item.repository.BillItemRepository;
import com.safepass.safebuilding.building.entity.Building;
import com.safepass.safebuilding.building.repository.BuildingRepository;
import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.common.firebase.entity.NotificationMessage;
import com.safepass.safebuilding.common.firebase.service.FirebaseMessagingService;
import com.safepass.safebuilding.common.meta.BillStatus;
import com.safepass.safebuilding.common.meta.RentContractStatus;
import com.safepass.safebuilding.common.service.MailSenderService;
import com.safepass.safebuilding.common.utils.ModelMapperCustom;
import com.safepass.safebuilding.common.validation.PaginationValidation;
import com.safepass.safebuilding.customer.entity.Customer;
import com.safepass.safebuilding.customer.repository.CustomerRepository;
import com.safepass.safebuilding.device.entity.Device;
import com.safepass.safebuilding.device.repository.DeviceRepository;
import com.safepass.safebuilding.flat.entity.Flat;
import com.safepass.safebuilding.flat.repository.FlatRepository;
import com.safepass.safebuilding.rent_contract.entity.RentContract;
import com.safepass.safebuilding.rent_contract.repository.RentContractRepository;
import com.safepass.safebuilding.service.entity.Service;
import com.safepass.safebuilding.service.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@org.springframework.stereotype.Service
public class BillServiceImpl implements BillService {
    @Autowired
    private PaginationValidation paginationValidation;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private RentContractRepository rentContractRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    protected DeviceRepository deviceRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private FirebaseMessagingService firebaseMessagingService;
    @Autowired
    private MailSenderService mailSenderService;
    @Autowired
    private BillItemRepository billItemRepository;
    @Autowired
    private FlatRepository flatRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private BillJDBC billJDBC;
    private ModelMapperCustom modelMapperCustom = new ModelMapperCustom();
    @Override
    public ResponseEntity<ResponseObject> getBillList(String customerId, int page, int size)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException
    {
        paginationValidation.validatePageSize(page, size);
        String queryTotalRow = BillUtils.getTotalRow(customerId);
        long totalRow = billJDBC.getTotalRow(queryTotalRow);
        int totalPage = (int) Math.ceil(1.0 * totalRow / size);

        Pagination pagination = new Pagination(page, size, totalPage);
        paginationValidation.validateMaxPageNumber(pagination);
        String queryGetBill = BillUtils.getBill(customerId);
        List<BillDTO> billDTOs = billJDBC.getBill(queryGetBill);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, billDTOs));
    }

    @Override
    public ResponseEntity<ResponseObject> getBillList(int page, int size)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException {
        paginationValidation.validatePageSize(page, size);
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Bill> billPage = billRepository.findAllByOrderByDateDesc(pageable);
        int totalPage = billPage.getTotalPages();
        Pagination pagination = new Pagination(page,size,totalPage);
        paginationValidation.validateMaxPageNumber(pagination);

        List<Bill> bills = billPage.getContent();
        List<com.safepass.safebuilding.bill.dto.Bill> dtoBills =
                modelMapperCustom.mapList(bills, com.safepass.safebuilding.bill.dto.Bill.class);
        for (int i = 0; i < bills.size(); i++) {
            RentContract rentContract = rentContractRepository
                    .findRentContractById(bills.get(i).getRentContract().getId()).get();
            Flat flat = flatRepository.findById(rentContract.getFlat().getId()).get();
            Building building = buildingRepository.findBuildingById(flat.getBuilding().getId()).get();
            Customer customer = customerRepository.findById(rentContract.getCustomer().getId()).get();
            com.safepass.safebuilding.bill.dto.Bill dtoBill = dtoBills.get(i);
            dtoBill.setBuildingName(building.getName());
            dtoBill.setRoom_number(flat.getRoomNumber());
            dtoBill.setCustomerName(customer.getFullname());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, dtoBills));
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    @Override
    public ResponseEntity<ResponseObject> createBill(BillCreate billCreate) throws NoSuchDataException {

        Optional<RentContract> rctemp = rentContractRepository
                .findRentContractByFlatIdAndStatus(
                        UUID.fromString(billCreate.getFlatId()),
                        RentContractStatus.VALID
                );

        if (rctemp.isPresent()) {

            RentContract rentContract = rctemp.get();
//            UUID rentContractId = rentContract.getId();
            int value = rentContract.getValue();
            ServiceDTO[] serviceDTOS = billCreate.getService();
            List<BillItem> billItems = new ArrayList<>();
            for (ServiceDTO s: serviceDTOS) {
                Optional<Service> stemp = serviceRepository.findServiceById(UUID.fromString(s.getId()));
                if (stemp.isPresent()) {
                    Service service = stemp.get();

                    value += service.getPrice() * s.getQuantity();
                }

            }
            Bill bill = new Bill(UUID.randomUUID(), rentContract, Date.valueOf(LocalDate.now()), value, BillStatus.UNPAID);
            bill = billRepository.save(bill);
            for (ServiceDTO s: serviceDTOS) {
                Optional<Service> stemp = serviceRepository.findServiceById(UUID.fromString(s.getId()));
                if (stemp.isPresent()) {
                    Service service = stemp.get();
                    BillItem billItem = new BillItem(UUID.randomUUID(), bill, s.getQuantity(), service.getPrice(), service);
                    billItems.add(billItem);
                }

            }

            for (BillItem bi: billItems) {
                billItemRepository.save(bi);
            }
            UUID customerId = rentContract.getCustomer().getId();
            sendNotiAndEmail(customerId);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));
        }
        throw new NoSuchDataException("This room does not have contract.");
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    @Override
    public ResponseEntity<ResponseObject> createBill() {
        List<RentContract> rentContracts = rentContractRepository.findAllByStatus(RentContractStatus.VALID);
        int rentContractSize = rentContracts.size();

        Date thisMonthStartDate = Date.valueOf(LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-01");
        for (int i = 0; i < rentContractSize; i++) {
            RentContract rentContract = rentContracts.get(i);
            Optional<Bill> btemp = billRepository.findBillByRentContractIdAndDateAfter(rentContract.getId(), thisMonthStartDate);
            Bill bill;
            if (btemp.isPresent()) {
                bill = btemp.get();
            } else {
                bill = new Bill(UUID.randomUUID(), rentContract, Date.valueOf(LocalDate.now()), rentContract.getValue(), BillStatus.UNPAID);
                bill = billRepository.save(bill);
            }
            int billValue = bill.getValue();

            //START: Add some mandatory services
            Service service = serviceRepository.findServiceByName("Electricity");
            int quantity = random(80,300);
            BillItem billItem = new BillItem(UUID.randomUUID(), bill, quantity, quantity*service.getPrice(), service);
            billItemRepository.save(billItem);
            billValue += quantity* service.getPrice();


            service = serviceRepository.findServiceByName("Water");
            quantity = random(10,30);
            billItem = new BillItem(UUID.randomUUID(), bill, quantity, quantity*service.getPrice(), service);
            billItemRepository.save(billItem);
            billValue += quantity* service.getPrice();

            service = serviceRepository.findServiceByName("Parking");
            quantity = random(1,3);
            billItem = new BillItem(UUID.randomUUID(), bill, quantity, quantity*service.getPrice(), service);
            billItemRepository.save(billItem);
            billValue += quantity* service.getPrice();

            service = serviceRepository.findServiceByName("HVAC");
            quantity = 1;
            billItem = new BillItem(UUID.randomUUID(), bill, quantity, quantity*service.getPrice(), service);
            billItemRepository.save(billItem);
            billValue += quantity* service.getPrice();
            //END: Add some mandatory services

            bill.setValue(billValue);
            billRepository.save(bill);
            //sendNotiAndEmail(rentContract.getCustomer().getId());
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));
    }


    private void sendNotiAndEmail(UUID customerId) {

        List<Device> devices = deviceRepository.findByCustomerId(customerId);
        Customer customer = customerRepository.findById(customerId).get();

        String title = "Safe Building Notification";
        String body = "Dear " + customer.getFullname() + ",\n" +
                "Our system notices that you have bill(s) that is unpaid so please check your information and pay the bill as soon as possible." +
                "\nThank you for spending time reading this letter.\nHave a good day.\nRegards,\nSafe Building Admin Team";
        for (Device device: devices) {
            NotificationMessage notificationMessage = new NotificationMessage(device.getToken(), title, body, new HashMap<String, String>());
            firebaseMessagingService.sendNotificationByToken(notificationMessage);
        }
        if (customer.getEmail() != null) {
            mailSenderService.sendMail(customer.getEmail(), title, body);
        }
    }
    private int random(int min, int max) {
        return min + (int) (Math.random() * (max-min+1));
    }
}
