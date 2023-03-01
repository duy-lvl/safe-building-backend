package com.safepass.safebuilding.customer.service.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.safepass.safebuilding.common.dto.Pagination;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.exception.InvalidDataException;
import com.safepass.safebuilding.common.exception.InvalidPageSizeException;
import com.safepass.safebuilding.common.exception.MaxPageExceededException;
import com.safepass.safebuilding.common.exception.NoSuchDataException;
import com.safepass.safebuilding.common.jwt.entity.response.TokenResponse;
import com.safepass.safebuilding.common.jwt.service.JwtService;
import com.safepass.safebuilding.common.meta.CustomerStatus;
import com.safepass.safebuilding.common.meta.Gender;
import com.safepass.safebuilding.common.meta.WalletStatus;
import com.safepass.safebuilding.common.security.user.UserPrinciple;
import com.safepass.safebuilding.common.utils.ModelMapperCustom;
import com.safepass.safebuilding.common.validation.PaginationValidation;
import com.safepass.safebuilding.customer.dto.*;
import com.safepass.safebuilding.customer.entity.Customer;
import com.safepass.safebuilding.customer.jdbc.CustomerJDBC;
import com.safepass.safebuilding.customer.repository.CustomerRepository;
import com.safepass.safebuilding.customer.service.CustomerService;
import com.safepass.safebuilding.customer.utils.CustomerUtils;
import com.safepass.safebuilding.customer.validation.CustomerInfoValidation;
import com.safepass.safebuilding.device.dto.DeviceDTO;
import com.safepass.safebuilding.device.entity.Device;
import com.safepass.safebuilding.device.repository.DeviceRepository;
import com.safepass.safebuilding.device.service.DeviceService;
import com.safepass.safebuilding.wallet.entity.Wallet;
import com.safepass.safebuilding.wallet.repository.WalletRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@Log4j2
public class CustomerServiceImpl implements CustomerService {

    private final ModelMapper modelMapper;
    private final ModelMapperCustom modelMapperCustom = new ModelMapperCustom();
    @Autowired
    private DeviceService deviceService;

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
    @Autowired
    private CustomerJDBC customerJDBC;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerInfoValidation customerInfoValidation;

    public CustomerServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * {@inheritDoc}
     *
     */
    public ResponseEntity<ResponseObject> getCustomerList(int page, int size)
            throws MaxPageExceededException, NoSuchDataException, InvalidPageSizeException
    {
        paginationValidation.validatePageSize(page, size);
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        int totalPage = customerPage.getTotalPages();

        Pagination pagination = new Pagination(page, size, totalPage);
        paginationValidation.validateMaxPageNumber(pagination);

        List<Customer> customers = customerPage.getContent();
        List<CustomerDTO> customerDTOs = modelMapperCustom.mapList(customers, CustomerDTO.class);

        for (CustomerDTO customer : customerDTOs) {
            List<Device> devices = deviceRepository.findByCustomerId(customer.getId());
            List<DeviceDTO> deviceDTOs = modelMapperCustom.mapList(devices, DeviceDTO.class);
            customer.setDevice(deviceDTOs);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, customerDTOs));

    }


    /**
     * {@inheritDoc}
     *
     */
    @Override
    public ResponseEntity<ResponseObject> login(String phone, String password)
    {
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
     *
     */
    @Override
    public ResponseEntity<ResponseObject> loginWithEmail(String email, String token)
            throws ExecutionException, InterruptedException
    {
        ResponseObject responseObject = null;
        Customer customer = customerRepository.findCustomerByEmail(email);
        TokenResponse tokenResponse = null;
        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdTokenAsync(token).get();
        String tokenEmail = firebaseToken.getEmail();
        boolean verifyEmail = firebaseToken.isEmailVerified();

        if (!tokenEmail.equals(email) || !(verifyEmail)) {
            responseObject = new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), "Wrong credentials information", null, null);
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }

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
            responseObject = new ResponseObject(HttpStatus.ACCEPTED.toString(), "Login successfully", null, userPrinciple);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(responseObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ResponseObject> getAccountList(int page, int size)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException
    {

        paginationValidation.validatePageSize(page, size);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        int totalPage = customerPage.getTotalPages();
        Pagination pagination = new Pagination(page, size, totalPage);
        paginationValidation.validateMaxPageNumber(pagination);
        List<Customer> customers = customerPage.getContent();
        List<AccountDTO> accountDTOs = modelMapperCustom.mapList(customers, AccountDTO.class);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, accountDTOs));

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<ResponseObject> addDevice(String customerId, String token) {
        Optional<Customer> customer = getCustomerById(UUID.fromString(customerId));
        if (customer.isPresent()) {
            Device device = deviceService.addToken(customer.get(), token);
            if (device != null) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Device existed", null, null));
    }


    public Optional<Customer> getCustomerById(UUID id) {
        return customerRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    public ResponseEntity<ResponseObject> filterCustomer(RequestObjectForFilter requestObjectForFilter)
            throws InvalidPageSizeException, MaxPageExceededException, NoSuchDataException
    {
        int page = requestObjectForFilter.getPage();
        int size = requestObjectForFilter.getSize();
        paginationValidation.validatePageSize(page, size);

        String queryTotalRow = CustomerUtils.filterTotalRow(requestObjectForFilter);
        Long totalRow = customerJDBC.getTotalRow(queryTotalRow);

        int totalPage = (int) Math.ceil(1.0 * totalRow / size);
        Pagination pagination = new Pagination(page, size, totalPage);
        paginationValidation.validateMaxPageNumber(pagination);

        String queryFilter = CustomerUtils.constructQueryForFilter(requestObjectForFilter, page - 1, size);
        List<CustomerDTO> customerDTOs = customerJDBC.getCustomerList(queryFilter);

        for (CustomerDTO customer : customerDTOs) {
            List<Device> devices = deviceRepository.findByCustomerId(customer.getId());
            List<DeviceDTO> deviceDTOs = modelMapperCustom.mapList(devices, DeviceDTO.class);
            customer.setDevice(deviceDTOs);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", pagination, customerDTOs));

    }

    /**
     * {@inheritDoc}
     *
     */
    @Transactional(rollbackFor = {SQLException.class, IllegalArgumentException.class})
    public ResponseEntity<ResponseObject> addCustomer(RequestObjectForCreateCustomer requestCustomer)
            throws InvalidDataException {
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

        Wallet wallet = Wallet.builder()
                .customer(customer)
                .id(UUID.randomUUID())
                .amount(0)
                .status(WalletStatus.ACTIVE)
                .build();
        walletRepository.save(wallet);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseObject(HttpStatus.CREATED.toString(), "Successfully", null, null));

    }

    /**
     * {@inheritDoc}
     *
     */
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

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public ResponseEntity<ResponseObject> getCustomer(String id) throws NoSuchDataException {
        Optional<Customer> customerOptional = customerRepository.findById(UUID.fromString(id));
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            CustomerInfo customerInfo = modelMapper.map(customer, CustomerInfo.class);
            String contractQuery = CustomerUtils.getContracts(customer.getId().toString());
            List<ContractDTO> contractDTOS = customerJDBC.getContracts(contractQuery);
            customerInfo.setContract(contractDTOS);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(HttpStatus.OK.toString(), "Successfully", null, customerInfo));
        }
        throw new NoSuchDataException("Customer not found");
    }
}
