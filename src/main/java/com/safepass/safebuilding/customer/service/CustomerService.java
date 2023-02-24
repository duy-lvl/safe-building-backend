package com.safepass.safebuilding.customer.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.customer.dto.RequestObjectForFilter;
import org.springframework.http.ResponseEntity;


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
    ResponseEntity<ResponseObject> getCustomerList(int page, int size);

    /**
     * Get account list for account management screen
     *
     * @param page {@code int}
     * @param size {@code int}
     * @return ResponseEntity<ResponseObject>
     */
    ResponseEntity<ResponseObject> getAccountList(int page, int size);

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
    ResponseEntity<ResponseObject> filterCustomer(RequestObjectForFilter requestObjectForFilter, int page, int size);
}
