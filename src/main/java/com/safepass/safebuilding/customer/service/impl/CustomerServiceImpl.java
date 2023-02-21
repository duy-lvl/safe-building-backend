package com.safepass.safebuilding.customer.service.impl;

import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.common.utils.ModelMapperCustom;
import com.safepass.safebuilding.common.validation.PaginationValidation;
import com.safepass.safebuilding.customer.dto.AccountDTO;
import com.safepass.safebuilding.customer.dto.CustomerDTO;
import com.safepass.safebuilding.customer.entity.Customer;
import com.safepass.safebuilding.customer.jdbc.CustomerJDBC;
import com.safepass.safebuilding.customer.repository.CustomerRepository;
import com.safepass.safebuilding.common.jwt.entity.response.TokenResponse;
import com.safepass.safebuilding.common.jwt.service.JwtService;
import com.safepass.safebuilding.common.meta.CustomerStatus;
import com.safepass.safebuilding.common.security.user.UserPrinciple;
import com.safepass.safebuilding.customer.service.CustomerService;
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
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class CustomerServiceImpl implements CustomerService {
    private ModelMapper modelMapper;
    @Autowired
    private DeviceService deviceService;
    private ModelMapperCustom modelMapperCustom = new ModelMapperCustom();
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PaginationValidation paginationValidation;
    @Autowired
    private DeviceRepository deviceRepository;
    public CustomerServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Autowired
    private CustomerJDBC customerJDBC;

    /**
     * {@inheritDoc}
     * */
    public ResponseEntity<ResponseObject> getCustomerList(int page, int size) {
        try {
            paginationValidation.validatePageSize(page, size);

            String queryTotalRow = CustomerServiceUtil.constructQueryForGetTotalRowGetAllCustomer();
            Long totalRow = customerJDBC.getTotalRow(queryTotalRow);

            int totalPage = (int) Math.ceil(1.0 * totalRow / size);
            Pagination pagination = new Pagination(page, size, totalPage);
            paginationValidation.validateMaxPageNumber(pagination);

            String queryGetAll = CustomerServiceUtil.constructQueryForGetAllCustomer(page - 1, size);
            List<CustomerDTO> customerDTOs = customerJDBC.getCustomerList(queryGetAll);

            for (CustomerDTO customer : customerDTOs) {
                List<Device> devices = deviceRepository.findByCustomerId(UUID.fromString(customer.getCustomerId()));
                List<DeviceDTO> deviceDTOs = modelMapperCustom.mapList(devices, DeviceDTO.class);
                customer.setDevice(deviceDTOs);
            }
            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, customerDTOs));
            return responseEntity;
        } catch (InvalidPageSizeException | MaxPageExceededException e) {
            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), e.getMessage(), null, null));
            return responseEntity;
        } catch (NoSuchDataException e) {
            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_FOUND.toString(), e.getMessage(), null, null));
            return responseEntity;
        }
    }


    /**
     * {@inheritDoc}
     * */
    @Override
    public ResponseEntity<ResponseObject> login(String phone, String password) {
        ResponseObject responseObject = null;
        Customer customer = customerRepository.findCustomerByPhone(phone);
        TokenResponse tokenResponse = null;
        if (customer == null) {
            log.error("Wrong credentials information");
            responseObject = new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), "Wrong credentials information", null, null);
        } else if (customer.getStatus().equals(CustomerStatus.INACTIVE)) {
            log.info("Customer found in database with username with inactive status:" + customer.getFullname());
            responseObject = new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), "Account has been locked", null, null);
        } else if (customer.getStatus().equals(CustomerStatus.ACTIVE)) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(phone + "&Customer", password)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
                String accessToken = jwtService.createAccessToken(userPrinciple);
                String refreshToken = jwtService.createRefreshToken(userPrinciple);
                tokenResponse = new TokenResponse(accessToken, refreshToken);
                userPrinciple.setTokenResponse(tokenResponse);
                responseObject = new ResponseObject(HttpStatus.ACCEPTED.toString(), "Login successfully", null, userPrinciple);
            } catch (AuthenticationException e) {
                if (e instanceof DisabledException) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Account has been locked.", null, null));
                }
                responseObject = new ResponseObject(HttpStatus.UNAUTHORIZED.toString(), "Invalid phone or password. Please try again.", null, null);
            }


        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseObject);
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public ResponseEntity<ResponseObject> loginWithEmail(String email) {
        ResponseObject responseObject = null;
        Customer customer = customerRepository.findCustomerByEmail(email);
        TokenResponse tokenResponse = null;

        if (customer == null) {
            log.error("Wrong credentials information");
            responseObject = new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), "Wrong credentials information", null, null);
        } else if (customer.getStatus().equals(CustomerStatus.INACTIVE)) {
            log.info("Customer found in database with username with inactive status:" + customer.getFullname());
            responseObject = new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), "Account has been locked", null, null);
        } else if (customer.getStatus().equals(CustomerStatus.ACTIVE)) {
            log.info("Customer found in database with username with active status:" + customer.getFullname());
            UserPrinciple userPrinciple = UserPrinciple.customerBuild(customer);
            String accessToken = jwtService.createAccessToken(userPrinciple);
            String refreshToken = jwtService.createRefreshToken(userPrinciple);
            tokenResponse = new TokenResponse(accessToken, refreshToken);
            userPrinciple.setTokenResponse(tokenResponse);
            if (userPrinciple == null) {
                log.info("null");
            }
            responseObject = new ResponseObject(HttpStatus.ACCEPTED.toString(), "Login successfully", null, userPrinciple);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseObject);
    }


    /**
     * {@inheritDoc}
     * */
    @Override
    public ResponseEntity<ResponseObject> getAccountList(int page, int size) {
        try {
            paginationValidation.validatePageSize(page, size);
            Pageable pageable = PageRequest.of(page-1, size);
            Page<Customer> customerPage = customerRepository.findAll(pageable);
            int totalPage = customerPage.getTotalPages();
            Pagination pagination = new Pagination(page, size, totalPage);
            paginationValidation.validateMaxPageNumber(pagination);
            List<Customer> customers = customerPage.getContent();
            List<AccountDTO> accountDTOs = modelMapperCustom.mapList(customers, AccountDTO.class);


            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, accountDTOs));
            return responseEntity;
        } catch (InvalidPageSizeException | MaxPageExceededException e) {
            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), e.getMessage(), null, null));
            return responseEntity;
        } catch (NoSuchDataException e) {
            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(HttpStatus.NOT_FOUND.toString(), e.getMessage(), null, null));
            return responseEntity;
        }
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public ResponseEntity<ResponseObject> addDevice(String customerId, String token) {
        Optional<Customer> customer = getCustomerById(UUID.fromString(customerId));
        if (customer.isPresent()) {
            Device device = deviceService.addToken(customer.get(), token);
            if (device != null) {
                ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));
                return responseEntity;
            }
        }

        ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Device existed", null, null));
        return responseEntity;
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public ResponseEntity<ResponseObject> filter(String name, String phone, String buildingId, String status) {
        return null;
    }

    public Optional<Customer> getCustomerById(UUID id) {
        return customerRepository.findById(id);
    }
}
