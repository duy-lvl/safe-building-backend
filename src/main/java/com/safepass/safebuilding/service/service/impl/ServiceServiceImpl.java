package com.safepass.safebuilding.service.service.impl;

import com.google.gson.Gson;
import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.common.firebase.service.IImageService;
import com.safepass.safebuilding.common.meta.ServiceStatus;
import com.safepass.safebuilding.common.utils.ModelMapperCustom;
import com.safepass.safebuilding.common.validation.PaginationValidation;
import com.safepass.safebuilding.service.dto.MobileServiceDTO;
import com.safepass.safebuilding.service.dto.RequestObjectForCreate;
import com.safepass.safebuilding.service.dto.RequestObjectForUpdate;
import com.safepass.safebuilding.service.dto.ServiceDTO;
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

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        String queryGet = ServiceServiceUtils.getQuery() +
                ServiceServiceUtils.appendSearchQuery(searchKey) +
                ServiceServiceUtils.appendSortQuery(sortBy, order) +
                ServiceServiceUtils.appendPagination(page-1, size);

        List<Service> services = serviceJdbc.searchService(queryGet);
        int totalRow = services.size();

        int totalPage = (int) Math.ceil(1.0 * totalRow / size);
        Pagination pagination = new Pagination(page, size, totalPage);
        paginationValidation.validateMaxPageNumber(pagination);
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
}
