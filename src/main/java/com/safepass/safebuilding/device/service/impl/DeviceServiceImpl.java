package com.safepass.safebuilding.device.service.impl;


import com.safepass.safebuilding.building.dto.BuildingDTO;
import com.safepass.safebuilding.building.entity.Building;
import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.utils.ModelMapperCustom;
import com.safepass.safebuilding.common.validation.PaginationValidation;
import com.safepass.safebuilding.customer.entity.Customer;
import com.safepass.safebuilding.device.dto.DeviceDTO;
import com.safepass.safebuilding.device.entity.Device;
import com.safepass.safebuilding.device.repository.DeviceRepository;
import com.safepass.safebuilding.device.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private PaginationValidation paginationValidation;
    @Autowired
    private DeviceRepository deviceRepository;
    private ModelMapperCustom modelMapper = new ModelMapperCustom();
    @Override
    public Device addToken(Customer customer, String token) {
        Device device = new Device(UUID.randomUUID(), token, customer);
        return deviceRepository.save(device);
    }

    @Override
    public ResponseEntity<ResponseObject> getAllDevice(int page, int size) {
        try {
            paginationValidation.validatePageSize(page, size);
            Pageable pageRequest = PageRequest.of(page - 1, size);
            Page<Device> devicePage = deviceRepository.findAll(pageRequest);
            int totalPage = devicePage.getTotalPages();
            Pagination pagination = new Pagination(page, size, totalPage);
            paginationValidation.validateMaxPageNumber(pagination);

            List<Device> devices = devicePage.getContent();
            List<DeviceDTO> deviceDTOs = modelMapper.mapList(devices, DeviceDTO.class);

            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, deviceDTOs));
            return responseEntity;
        } catch (InvalidPageSizeException | MaxPageExceededException e) {
            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), e.getMessage(), null, null));
            return responseEntity;
        }
    }
}
