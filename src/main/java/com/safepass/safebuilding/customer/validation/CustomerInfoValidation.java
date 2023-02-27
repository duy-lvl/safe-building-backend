package com.safepass.safebuilding.customer.validation;

import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.common.meta.CustomerStatus;
import com.safepass.safebuilding.common.meta.Gender;
import com.safepass.safebuilding.customer.dto.RequestObjectForCreateCustomer;
import com.safepass.safebuilding.customer.dto.RequestObjectForUpdateCustomer;
import org.springframework.stereotype.Service;

@Service
public class CustomerInfoValidation {
    public static final String EMAIL_REGEX_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public static final String PHONE_REGEX_PATTERN = "(^$|[0-9]{10})";

    public static final String DATE_REGEX_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";

    public void validateCreate(RequestObjectForCreateCustomer requestObj) throws InvalidDataException {
        if (!requestObj.getPhone().matches(PHONE_REGEX_PATTERN)) {
            throw new InvalidDataException("Phone number is invalid");
        }
        if (requestObj.getEmail() != null) {
            if (!requestObj.getEmail().matches(EMAIL_REGEX_PATTERN)) {
                throw new InvalidDataException("Email is invalid");
            }
        }
        if (requestObj.getPassword() == null || requestObj.getFullName() == null) {
            throw new InvalidDataException("Field cannot be null");
        }
    }

    public void validateUpdate(RequestObjectForUpdateCustomer requestObj) throws InvalidDataException {
        if (!requestObj.getPhone().matches(PHONE_REGEX_PATTERN)) {
            throw new InvalidDataException("Phone number is invalid");
        }
        if (requestObj.getEmail() != null) {
            if (!requestObj.getEmail().matches(EMAIL_REGEX_PATTERN)) {
                throw new InvalidDataException("Email is invalid");
            }
        }
        if (requestObj.getAddress() == null || requestObj.getFullname() == null || requestObj.getId() == null ||
            requestObj.getCitizenId() == null || requestObj.getGender() == null || requestObj.getDateOfBirth() == null) {
            throw new InvalidDataException("Field cannot be null");
        }
        if (!requestObj.getDateOfBirth().matches(DATE_REGEX_PATTERN)) {
            throw new InvalidDataException("Wrong date format");
        }
        if (!(requestObj.getStatus().equals(CustomerStatus.ACTIVE.toString())
                || requestObj.getStatus().equals(CustomerStatus.INACTIVE.toString()))) {
            throw new InvalidDataException("Status must be " + CustomerStatus.values()[0] + " or " + CustomerStatus.values()[1] + "");
        }
        if (!(requestObj.getGender().equals(Gender.MALE.toString())
                || requestObj.getGender().equals(Gender.FEMALE.toString())
                || requestObj.getGender().equals(Gender.OTHER.toString()))) {
            throw new InvalidDataException("Gender must be " + Gender.values()[0] + ", or " + Gender.values()[1]
                    + ", or " + Gender.values()[2]);
        }
    }
}
