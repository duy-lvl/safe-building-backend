package com.safepass.safebuilding.rent_contract.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface RentContractService {

    /**
     * Upload file to firebase and insert file link to the database
     *
     * @param files {@code MultipartFile[]}
     * @param customerId {@code String}
     * @param rentContractId {@code String}
     * @param flatId {@code String}
     *
     * @return ResponseEntity<ResponseObject>
     *
     * @throws IOException
     */
    ResponseEntity<ResponseObject> uploadFile(MultipartFile[] files, String customerId, String rentContractId, String flatId)
            throws IOException;



    /**
     * Get contract list
     *
     * @param page {@code int}
     * @param size {@code int}
     * @return ResponseEntity<ResponseObject>
     */
    ResponseEntity<ResponseObject> getList(int page, int size, String searchKey, String sortBy, String order) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException;
    /**
     * Get account list for account management screen
     *
     * @param files {@code MultipartFile[]}
     * @param requestObject {@code String}
     * <p>Request object is a JSON string {customerId, flatId, expiryDate, value}</p>
     * @return ResponseEntity<ResponseObject>
     * @throws IOException
     * @throws SQLException
     * @throws InvalidDataException
     */

    ResponseEntity<ResponseObject> updateContract(MultipartFile[] files, String requestObject)
            throws IOException, SQLException, InvalidDataException;

    /**
     * Get contract by id
     *
     * @param id {@code String}
     * @return ResponseEntity<ResponseObject>
     */
    ResponseEntity<ResponseObject> getContractById(String id);


    /**
     * Get account list for account management screen
     *
     * @param files {@code MultipartFile[]}
     * @param requestObject {@code String}
     * <p>Request object is a JSON string {customerId, flatId, expiryDate, value}</p>
     * @return ResponseEntity<ResponseObject>
     * @throws IOException
     * @throws SQLException
     * @throws InvalidDataException
     */
    ResponseEntity<ResponseObject> createContract(MultipartFile[] files, String requestObject, String[] deviceTokens)
            throws IOException, SQLException, InvalidDataException;
    ResponseEntity<ResponseObject> deleteContract(String contractId) throws NoSuchDataException;
}
