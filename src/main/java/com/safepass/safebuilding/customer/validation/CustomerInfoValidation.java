package com.safepass.safebuilding.customer.validation;

import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.common.meta.CustomerStatus;
import com.safepass.safebuilding.common.meta.Gender;
import com.safepass.safebuilding.customer.dto.RequestObjectForCreateCustomer;
import com.safepass.safebuilding.customer.dto.RequestObjectForUpdateCustomer;
import com.safepass.safebuilding.customer.entity.Customer;
import com.safepass.safebuilding.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerInfoValidation {
    public static final String EMAIL_REGEX_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public static final String PHONE_REGEX_PATTERN = "(^$|[0-9]{10})";

    public static final String DATE_REGEX_PATTERN = "^\\d{4}-\\d{2}-\\d{2}$";

    public static final String PASSWORD_REGEX_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";


    @Autowired
    private CustomerRepository customerRepository;

    public void validateCreate(RequestObjectForCreateCustomer requestObj) throws InvalidDataException {
        String phone = requestObj.getPhone();
        if (!phone.matches(PHONE_REGEX_PATTERN)) {
            throw new InvalidDataException("Phone number is invalid");
        }
        if (customerRepository.findCustomerByPhone(phone) != null) {
            throw new InvalidDataException("Phone is existed");
        }
        String email = requestObj.getEmail();
        if (email != null) {
            if (!email.matches(EMAIL_REGEX_PATTERN) && !email.matches("")) {
                throw new InvalidDataException("Email is invalid");
            }
        }

        if (customerRepository.findCustomerByEmail(email) != null) {
            throw new InvalidDataException("Email existed");
        }
        if (!requestObj.getPassword().matches(PASSWORD_REGEX_PATTERN)) {
            throw new InvalidDataException("Password must consist of number and character");
        }
        if (requestObj.getAddress() == null ||  requestObj.getAddress().matches("") ) {
            throw new InvalidDataException("Address cannot be null");
        }

        if (requestObj.getFullName() == null || requestObj.getFullName().matches("")) {
            throw new InvalidDataException("Full name cannot be null");
        }

        if (requestObj.getCitizenId() == null || requestObj.getCitizenId().matches("") ) {
            throw new InvalidDataException("Citizen id cannot be null");
        }

        if (requestObj.getGender() == null || requestObj.getGender().matches("")) {
            throw new InvalidDataException("Gender cannot be null");
        }
        if (!requestObj.getDateOfBirth().matches(DATE_REGEX_PATTERN)) {
            throw new InvalidDataException("Wrong date format");
        }
        if (!(requestObj.getGender().equals(Gender.MALE.toString())
                || requestObj.getGender().equals(Gender.FEMALE.toString())
                || requestObj.getGender().equals(Gender.OTHER.toString()))) {
            throw new InvalidDataException("Gender must be " + Gender.values()[0] + ", or " + Gender.values()[1]
                    + ", or " + Gender.values()[2]);
        }

    }

    public void validateUpdate(RequestObjectForUpdateCustomer requestObj) throws InvalidDataException {
        String phone = requestObj.getPhone();
        String email = requestObj.getEmail();
        if (!phone.matches(PHONE_REGEX_PATTERN)) {
            throw new InvalidDataException("Phone number is invalid");
        }


        if (email != null) {
            if (!email.matches(EMAIL_REGEX_PATTERN) && !email.matches("")) {
                throw new InvalidDataException("Email is invalid");
            }
        }
        Customer customer = customerRepository.findCustomerByPhone(phone);
        if (customer != null && !customer.getId().toString().equals(requestObj.getId())) {
            if (customer.getPhone().equals(phone)) {
                throw new InvalidDataException("Phone number existed");
            }
        }
        customer = customerRepository.findCustomerByEmail(email);
        if (customer != null &&  !customer.getId().toString().equals(requestObj.getId())) {
            if (customer.getEmail().equals(email)) {
                throw new InvalidDataException("Email existed");
            }
        }
        if (requestObj.getAddress() == null ||  requestObj.getAddress().matches("") ) {
            throw new InvalidDataException("Address cannot be null");
        }

        if (requestObj.getFullname() == null || requestObj.getFullname().matches("")) {
            throw new InvalidDataException("Full name cannot be null");
        }

        if (requestObj.getId() == null || requestObj.getId().matches("")) {
            throw new InvalidDataException("ID cannot be null");
        }

        if (requestObj.getCitizenId() == null || requestObj.getCitizenId().matches("") ) {
            throw new InvalidDataException("Citizen id cannot be null");
        }

        if (requestObj.getGender() == null || requestObj.getGender().matches("")) {
            throw new InvalidDataException("Gender cannot be null");
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
        if (requestObj.getAddress() == null || requestObj.getFullname() == null || requestObj.getId() == null ||
                requestObj.getCitizenId() == null || requestObj.getGender() == null || requestObj.getDateOfBirth() == null) {
            throw new InvalidDataException("Field cannot be null");
        }
    }

    public void validateCommon(RequestObjectForUpdateCustomer requestObj) throws InvalidDataException {





    }
}
