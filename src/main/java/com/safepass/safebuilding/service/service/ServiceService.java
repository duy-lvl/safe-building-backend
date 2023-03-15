package com.safepass.safebuilding.service.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ServiceService {

    ResponseEntity<ResponseObject> getAllService(int page, int size)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException;

    ResponseEntity<ResponseObject> getActiveServices(int page, int size);

    ResponseEntity<ResponseObject> getServiceList(int page, int size, String searchKey, String sortBy, String order)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException;

    ResponseEntity<ResponseObject> createService(MultipartFile[] icon, String requestObject) throws IOException, InvalidDataException;
}
