package com.safepass.safebuilding.customer.service.impl;

import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.common.meta.FlatStatus;
import com.safepass.safebuilding.common.meta.Gender;
import com.safepass.safebuilding.common.meta.RentContractStatus;
import com.safepass.safebuilding.common.utils.ModelMapperCustom;
import com.safepass.safebuilding.common.validation.PaginationValidation;
import com.safepass.safebuilding.customer.dto.*;
import com.safepass.safebuilding.customer.entity.Customer;
import com.safepass.safebuilding.customer.jdbc.CustomerJDBC;
import com.safepass.safebuilding.customer.repository.CustomerRepository;
import com.safepass.safebuilding.common.jwt.entity.response.TokenResponse;
import com.safepass.safebuilding.common.jwt.service.JwtService;
import com.safepass.safebuilding.common.meta.CustomerStatus;
import com.safepass.safebuilding.common.security.user.UserPrinciple;
import com.safepass.safebuilding.customer.service.CustomerService;
import com.safepass.safebuilding.customer.validation.CustomerInfoValidation;
import com.safepass.safebuilding.device.dto.DeviceDTO;
import com.safepass.safebuilding.device.entity.Device;
import com.safepass.safebuilding.device.repository.DeviceRepository;
import com.safepass.safebuilding.device.service.DeviceService;
import com.safepass.safebuilding.flat.entity.Flat;
import com.safepass.safebuilding.flat.jdbc.FlatJDBC;
import com.safepass.safebuilding.flat.repository.FlatRepository;
import com.safepass.safebuilding.flat.service.impl.FlatServiceUtil;
import com.safepass.safebuilding.rent_contract.entity.RentContract;
import com.safepass.safebuilding.rent_contract.repository.RentContractRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
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
    @Autowired
    private FlatRepository flatRepository;
    @Autowired
    private RentContractRepository rentContractRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FlatJDBC flatJDBC;
    @Autowired
    private CustomerInfoValidation customerInfoValidation;

    /**
     * {@inheritDoc}
     * */
    public ResponseEntity<ResponseObject> getCustomerList(int page, int size) throws MaxPageExceededException, NoSuchDataException, InvalidPageSizeException {

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
    public ResponseEntity<ResponseObject> getAccountList(int page, int size) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException {

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


    public Optional<Customer> getCustomerById(UUID id) {
        return customerRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     *
     * */
    public ResponseEntity<ResponseObject> filterCustomer(RequestObjectForFilter requestObjectForFilter, int page, int size) throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException {

            paginationValidation.validatePageSize(page, size);

            String queryTotalRow = CustomerServiceUtil.filterTotalRow(requestObjectForFilter);
            Long totalRow = customerJDBC.getTotalRow(queryTotalRow);

            int totalPage = (int) Math.ceil(1.0 * totalRow / size);
            Pagination pagination = new Pagination(page, size, totalPage);
            paginationValidation.validateMaxPageNumber(pagination);

            String queryFilter = CustomerServiceUtil.constructQueryForFilter(requestObjectForFilter, page-1, size);
            List<CustomerDTO> customerDTOs = customerJDBC.getCustomerList(queryFilter);

            for (CustomerDTO customer : customerDTOs) {
                List<Device> devices = deviceRepository.findByCustomerId(UUID.fromString(customer.getCustomerId()));
                List<DeviceDTO> deviceDTOs = modelMapperCustom.mapList(devices, DeviceDTO.class);
                customer.setDevice(deviceDTOs);
            }
            ResponseEntity<ResponseObject> responseEntity = ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, customerDTOs));
            return responseEntity;

    }

    /**
     * {@inheritDoc}
     *
     */
    @Transactional(rollbackFor = {SQLException.class, IllegalArgumentException.class})
    public ResponseEntity<ResponseObject> addCustomer(RequestObjectForCreateCustomer requestCustomer) throws InvalidDataException, SQLException {
        customerInfoValidation.validateCreate(requestCustomer);
            UUID customerId = UUID.randomUUID();

        Customer customer = Customer.builder()
                .id(customerId)
                .phone(requestCustomer.getPhone())
                .address(requestCustomer.getAddress())
                .dateJoin(Date.valueOf(LocalDate.now().toString()))
                .dateOfBirth(Date.valueOf(requestCustomer.getDateOfBirth()))
                .password(passwordEncoder.encode(requestCustomer.getPassword()))
                .fullname(requestCustomer.getFullName())
                .gender(Gender.valueOf(requestCustomer.getGender()))
                .citizenId(requestCustomer.getCitizenId())
                .status(CustomerStatus.ACTIVE)
                .build();
        customerRepository.save(customer);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));

    }

    @Override
    public ResponseEntity<ResponseObject> updateCustomer(RequestObjectForUpdateCustomer requestCustomer)
            throws InvalidDataException
    {
        customerInfoValidation.validateUpdate(requestCustomer);

        Optional<Customer> temp = customerRepository.findById(UUID.fromString(requestCustomer.getId()));
        if (temp.isPresent()) {
            Customer customer = temp.get();
            customer.setPhone(requestCustomer.getPhone());
            customer.setEmail(requestCustomer.getEmail());
            customer.setAddress(requestCustomer.getAddress());
            customer.setGender(Gender.valueOf(requestCustomer.getGender()));
            customer.setFullname(requestCustomer.getFullname());
            customer.setDateOfBirth(Date.valueOf(requestCustomer.getDateOfBirth()));
            customer.setCitizenId(requestCustomer.getCitizenId());
            customer.setStatus(CustomerStatus.valueOf(requestCustomer.getStatus()));

            customerRepository.save(customer);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));
        }
        throw new InvalidDataException("Customer does not exist");
    }

    @Override
    public ResponseEntity<ResponseObject> getCustomer(String id) throws NoSuchDataException {
        Optional<Customer> customerOptional = customerRepository.findById(UUID.fromString(id));
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            CustomerInfo customerInfo = modelMapper.map(customer, CustomerInfo.class);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, customerInfo));
        }
        throw new NoSuchDataException("Customer not found");
    }
}
