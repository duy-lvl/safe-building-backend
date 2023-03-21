package com.safepass.safebuilding.service.service.impl;

import com.google.gson.Gson;
import com.safepass.safebuilding.bill.entity.Bill;
import com.safepass.safebuilding.bill.repository.BillRepository;
import com.safepass.safebuilding.bill_item.entity.BillItem;
import com.safepass.safebuilding.bill_item.repository.BillItemRepository;
import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.common.firebase.service.IImageService;
import com.safepass.safebuilding.common.meta.BillStatus;
import com.safepass.safebuilding.common.meta.ServiceStatus;
import com.safepass.safebuilding.common.utils.ModelMapperCustom;
import com.safepass.safebuilding.common.validation.PaginationValidation;
import com.safepass.safebuilding.rent_contract.entity.RentContract;
import com.safepass.safebuilding.rent_contract.repository.RentContractRepository;
import com.safepass.safebuilding.service.dto.*;
import com.safepass.safebuilding.service.entity.Service;
import com.safepass.safebuilding.service.jdbc.ServiceJdbc;
import com.safepass.safebuilding.service.repository.ServiceRepository;
import com.safepass.safebuilding.service.service.ServiceService;
import com.safepass.safebuilding.service.validation.ServiceValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    private ModelMapperCustom modelMapper = new ModelMapperCustom();
    @Autowired
    private PaginationValidation paginationValidation;
    @Autowired
    private ServiceJdbc serviceJdbc;
    @Autowired
    private IImageService imageService;
    @Autowired
    private ServiceValidation serviceValidation;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private RentContractRepository rentContractRepository;
    @Autowired
    private BillItemRepository billItemRepository;
    @Override
    public ResponseEntity<ResponseObject> getAllService(int page, int size)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException
    {
        paginationValidation.validatePageSize(page, size);
        Pageable pageRequest = PageRequest.of(page - 1, size);
        Page<Service> servicePage = serviceRepository.findAll(pageRequest);

        int totalPage = servicePage.getTotalPages();
        Pagination pagination = new Pagination(page, size, totalPage);
        paginationValidation.validateMaxPageNumber(pagination);

        List<Service> services = servicePage.getContent();
        List<ServiceDTO> serviceDTOs = modelMapper.mapList(services, ServiceDTO.class);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, serviceDTOs));

    }

    @Override
    public ResponseEntity<ResponseObject> getActiveServices(int page, int size) {
        try {
            paginationValidation.validatePageSize(page, size);
            Pageable pageRequest = PageRequest.of(page - 1, size);
            Page<Service> servicePage = serviceRepository.findByStatus(ServiceStatus.ACTIVE, pageRequest);
            int totalPage = servicePage.getTotalPages();
            Pagination pagination = new Pagination(page, size, totalPage);
            paginationValidation.validateMaxPageNumber(pagination);

            List<Service> services = servicePage.getContent();
            List<MobileServiceDTO> serviceDTOs = modelMapper.mapList(services, MobileServiceDTO.class);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, serviceDTOs));
        } catch (InvalidPageSizeException | MaxPageExceededException | NoSuchDataException e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "No data", null, null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> getServiceList(int page, int size, String searchKey, String sortBy, String order) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException {
        paginationValidation.validatePageSize(page, size);
        String queryTotalRow = ServiceServiceUtils.getTotalRow()+
                ServiceServiceUtils.appendSearchQuery(searchKey) +
                ServiceServiceUtils.appendSortQuery(sortBy, order) +
                ServiceServiceUtils.appendPagination(page-1, size);
        long totalRow = serviceJdbc.getTotalRow(queryTotalRow);
        int totalPage = (int) Math.ceil(1.0 * totalRow / size);
        Pagination pagination = new Pagination(page, size, totalPage);
        paginationValidation.validateMaxPageNumber(pagination);


        String queryGet = ServiceServiceUtils.getQuery() +
                ServiceServiceUtils.appendSearchQuery(searchKey) +
                ServiceServiceUtils.appendSortQuery(sortBy, order) +
                ServiceServiceUtils.appendPagination(page-1, size);

        List<Service> services = serviceJdbc.searchService(queryGet);

//        List<ServiceDTO> serviceDTOs = modelMapper.mapList(services, ServiceDTO.class);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, services));
    }

    @Override
    public ResponseEntity<ResponseObject> createService(MultipartFile[] iconImg, String requestObject)
            throws IOException, InvalidDataException {
        Gson gson = new Gson();
        RequestObjectForCreate serviceRequestObj = gson.fromJson(requestObject, RequestObjectForCreate.class);

        String icon = imageService.create(iconImg);
        Service service = serviceValidation.validateCreate(serviceRequestObj);
        service.setIcon(icon);
        serviceRepository.save(service);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));
    }

    @Override
    public ResponseEntity<ResponseObject> getServiceById(String id) throws NoSuchDataException {
        Optional<Service> stemp = serviceRepository.findServiceById(UUID.fromString(id));
        if (stemp.isPresent()) {
            Service service = stemp.get();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, service));
        }
        throw new NoSuchDataException("Not found");
    }

    @Override
    public ResponseEntity<ResponseObject> updateService(RequestObjectForUpdate requestObject) throws InvalidDataException {
        Service service = serviceValidation.validateUpdate(requestObject);
        serviceRepository.save(service);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));
    }

    @Override
    public ResponseEntity<ResponseObject> updateIcon(String serviceId, MultipartFile[] newIcon) throws IOException, InvalidDataException {

        Optional<Service> stemp = serviceRepository.findServiceById(UUID.fromString(serviceId));
        if (stemp.isPresent()) {
            Service service = stemp.get();
            String oldLink = service.getIcon();
            if (oldLink != null) {
                imageService.delete(oldLink);
            }

            String newLink = imageService.create(newIcon);
            service.setIcon(newLink);
            serviceRepository.save(service);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));
        }
        throw new InvalidDataException("Service does not exist");
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public ResponseEntity<ResponseObject> addService(AddServiceDTO addServiceDTO) {
        Date thisMonthStartDate = Date.valueOf(LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-01");
        Optional<Bill> btemp = billRepository
                .findBillByRentContractIdAndDateAfter(UUID.fromString(addServiceDTO.getContractId()), thisMonthStartDate);
        Bill bill;
        RentContract rentContract = rentContractRepository.findRentContractById(UUID.fromString(addServiceDTO.getContractId())).get();
        if (!btemp.isPresent()) {
            bill = new Bill(UUID.randomUUID(), rentContract, Date.valueOf(LocalDate.now()), rentContract.getValue(), BillStatus.UNPAID);
            bill = billRepository.save(bill);
        } else {
            bill = btemp.get();
        }
        Service service = serviceRepository.findServiceById(UUID.fromString(addServiceDTO.getServiceid())).get();

        BillItem billItem = new BillItem(
                UUID.randomUUID(),
                bill,
                addServiceDTO.getQuantity(),
                addServiceDTO.getQuantity()*service.getPrice(),
                service
        );
        int billValue = bill.getValue();
        billItemRepository.save(billItem);
        billValue += addServiceDTO.getQuantity() * service.getPrice();
        bill.setValue(billValue);
        billRepository.save(bill);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));
    }
}
