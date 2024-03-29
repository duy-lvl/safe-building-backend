package com.safepass.safebuilding.service.controller;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.service.dto.AddServiceDTO;
import com.safepass.safebuilding.service.dto.RequestObjectForUpdate;
import com.safepass.safebuilding.service.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/v1/services")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

//    @GetMapping("/services")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<ResponseObject> adminGetList(
//            @RequestParam(name = "page", defaultValue = "1")  int page,
//            @RequestParam(name = "size", defaultValue = "10") int size
//    ) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException {
//        return serviceService.getAllService(page, size);
//    }

    @GetMapping("/active")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<ResponseObject> mobileGetList(
            @RequestParam(name = "page", defaultValue = "1")  int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return serviceService.getActiveServices(page, size);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> searchService(
            @RequestParam(name = "page", defaultValue = "1")  int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "searchKey", defaultValue = "") String searchKey,
            @RequestParam(name = "sortBy", defaultValue = "") String sortBy,
            @RequestParam(name = "order", defaultValue = "") String order
    ) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException {
        return serviceService.getServiceList(page, size, searchKey, sortBy, order);
    }

    @GetMapping("/{serviceId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> searchServiceById(
            @PathVariable String serviceId) throws NoSuchDataException {
        return serviceService.getServiceById(serviceId);
    }
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> createService(
            @RequestParam MultipartFile[] icon,
            @RequestParam String requestObject) throws IOException, InvalidDataException {
        return serviceService.createService(icon, requestObject);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> updateService(
            @RequestBody RequestObjectForUpdate requestObject
    ) throws InvalidDataException {
        return serviceService.updateService(requestObject);
    }

    @PutMapping ("/icon")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> updateServiceIcon(
            @RequestParam String serviceId,
            @RequestParam MultipartFile[] newIcon
    ) throws IOException, InvalidDataException {
        return serviceService.updateIcon(serviceId, newIcon);
    }


}
