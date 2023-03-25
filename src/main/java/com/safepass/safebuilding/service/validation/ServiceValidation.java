package com.safepass.safebuilding.service.validation;

import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.common.meta.ServiceStatus;
import com.safepass.safebuilding.service.dto.RequestObjectForCreate;
import com.safepass.safebuilding.service.dto.RequestObjectForUpdate;
import com.safepass.safebuilding.service.entity.Service;
import com.safepass.safebuilding.service.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Arrays;
import java.util.UUID;


@org.springframework.stereotype.Service
public class ServiceValidation {

    @Autowired
    private ServiceRepository serviceRepository;
    public static final String DIGIT_REGEX = "\\d+";
    public Service validateCreate(RequestObjectForCreate requestObjectForCreate) throws InvalidDataException {

        String name = requestObjectForCreate.getName();
        String price = requestObjectForCreate.getPrice();
        String status = requestObjectForCreate.getStatus();
        if (name == null) {
            throw new InvalidDataException("Name cannot be null");
        }
        if (!price.matches(DIGIT_REGEX)) {
            throw new InvalidDataException("Invalid price");
        }
        ServiceStatus[] statuses = ServiceStatus.values();
        boolean isValidStatus = false;
        for (ServiceStatus ss: statuses) {
            if (ss.toString().equals(status.toUpperCase())) {
                isValidStatus = true;
                break;
            }
        }
        if (!isValidStatus) {
            throw new InvalidDataException("Invalid status. Status must be " + Arrays.toString(statuses));
        }

        return Service.builder()
                .id(UUID.randomUUID())
                .price(Integer.parseInt(price))
                .description(requestObjectForCreate.getDescription())
                .status(ServiceStatus.valueOf(status))
                .name(name)
                .build();
    }

    public Service validateUpdate(RequestObjectForUpdate requestObjectForUpdate) throws InvalidDataException {

        String id = requestObjectForUpdate.getId();
        if (!serviceRepository.existsById(UUID.fromString(id))) {
            throw new InvalidDataException("Service does not exist");
        }
        String name = requestObjectForUpdate.getName();
        String price = requestObjectForUpdate.getPrice();
        String status = requestObjectForUpdate.getStatus();
        if (name == null) {
            throw new InvalidDataException("Name cannot be null");
        }
        if (!price.matches(DIGIT_REGEX)) {
            throw new InvalidDataException("Invalid price");
        }
        ServiceStatus[] statuses = ServiceStatus.values();
        boolean isValidStatus = false;
        for (ServiceStatus ss: statuses) {
            if (ss.toString().equals(status.toUpperCase())) {
                isValidStatus = true;
                break;
            }
        }
        if (!isValidStatus) {
            throw new InvalidDataException("Invalid status. Status must be " + Arrays.toString(statuses));
        }

        return Service.builder()
                .id(UUID.fromString(id))
                .price(Integer.parseInt(price))
                .description(requestObjectForUpdate.getDescription())
                .status(ServiceStatus.valueOf(status))
                .name(name)
                .build();
    }
}
