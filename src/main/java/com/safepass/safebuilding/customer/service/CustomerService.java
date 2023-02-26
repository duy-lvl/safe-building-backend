package com.safepass.safebuilding.customer.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.customer.dto.RequestObjectForCreateCustomer;
import com.safepass.safebuilding.customer.dto.RequestObjectForFilter;
import com.safepass.safebuilding.customer.dto.RequestObjectForUpdateCustomer;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;


public interface CustomerService {
    /**
     * Login with phone and password for mobile
     *
     * @param phoneOrEmail {@code String}
     * @param password {@code String}
     * @return ResponseEntity<ResponseObject>
     */
    ResponseEntity<ResponseObject> login(String phoneOrEmail, String password);

    /**
     * Login with email for mobile
     *
     * @param email {@code String}
     * @return ResponseEntity<ResponseObject>
     */
    ResponseEntity<ResponseObject> loginWithEmail(String email);

    /**
     * Get customer list for customer management screen
     *
     * @param page {@code int}
     * @param size {@code int}
     * @return ResponseEntity<ResponseObject>
     */
    ResponseEntity<ResponseObject> getCustomerList(int page, int size) throws MaxPageExceededException, NoSuchDataException, InvalidPageSizeException;

    /**
     * Get account list for account management screen
     *
     * @param page {@code int}
     * @param size {@code int}
     * @return ResponseEntity<ResponseObject>
     */
    ResponseEntity<ResponseObject> getAccountList(int page, int size) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException;

    /**
     * Add token to customer
     *
     * @param customerId {@code String}
     * @param token {@code String}
     * @return {@code ResponseEntity<{@code ResponseObject}>}
     */
    ResponseEntity<ResponseObject> addDevice(String customerId, String token);


    /**
     * Filter customer by name/phone
     * AND/OR building name
     * AND/OR customer status
     *
     * @param requestObjectForFilter {@code RequestObjectForFilter}
     * @param page {@code int}
     * @param size {@code int}
     *
     * @return ResponseEntity<ResponseObject>
     */
    ResponseEntity<ResponseObject> filterCustomer(RequestObjectForFilter requestObjectForFilter, int page, int size) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException;

    /**
     * Create a new customer, update flat status to UNAVAILABLE, create a rent contract
     *
     * @param requestCustomer {@code RequestObjectForCreateCustomer}
     *
     * @return ResponseEntity<ResponseObject>
     * @throws InvalidDataException
     * @throws SQLException
     */
    ResponseEntity<ResponseObject> addCustomer(RequestObjectForCreateCustomer requestCustomer) throws InvalidDataException, SQLException;

    /**
     * Update customer information
     *
     * @param requestCustomer {@code RequestObjectForUpdateCustomer}
     *
     * @return ResponseEntity<ResponseObject>
     *
     */
    ResponseEntity<ResponseObject> updateCustomer(RequestObjectForUpdateCustomer requestCustomer) throws InvalidDataException;

    /**
     * Get customer by id
     *
     * @param id {@code String}
     *
     * @return ResponseEntity<ResponseObject>
     * */
    ResponseEntity<ResponseObject> getCustomer(String id) throws NoSuchDataException;

}
