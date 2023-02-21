package com.safepass.safebuilding.admin.service.impl;

import com.safepass.safebuilding.admin.entity.Admin;
import com.safepass.safebuilding.admin.repository.AdminRepository;
import com.safepass.safebuilding.admin.service.AdminService;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.jwt.entity.response.TokenResponse;
import com.safepass.safebuilding.common.jwt.service.JwtService;
import com.safepass.safebuilding.common.meta.AdminStatus;
import com.safepass.safebuilding.common.security.user.UserPrinciple;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AdminServiceImpl implements AdminService {
    private final ModelMapper modelMapper;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public AdminServiceImpl(AdminRepository adminRepository, ModelMapper modelMapper) {
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Login with phone and password for web
     *
     * @param phone
     * @param password
     * @return ResponseEntity<ResponseObject>
     */
    @Override
    public ResponseEntity<ResponseObject> login(String phone, String password) {
        ResponseObject responseObject = null;
        Admin admin = adminRepository.findAdminByPhone(phone);
        TokenResponse tokenResponse = null;
        if (admin == null) {
            log.error("Wrong credentials information");
            responseObject = new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), "Wrong credentials information", null, null);
        } else if (admin.getStatus().equals(AdminStatus.INACTIVE)) {
            log.info("Admin found in database with username with inactive status:" + admin.getFullname());
            responseObject = new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), "Account has been locked", null, null);
        } else if (admin.getStatus().equals(AdminStatus.ACTIVE)) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(phone + "&Admin", password)
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
     * Login with email for web
     *
     * @param email
     * @return ResponseEntity<ResponseObject>
     */
    @Override
    public ResponseEntity<ResponseObject> loginWithEmail(String email) {
        ResponseObject responseObject = null;
        Admin admin = adminRepository.findAdminByEmail(email);
        TokenResponse tokenResponse = null;
        if (admin == null) {
            log.error("Wrong credentials information");
            responseObject = new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), "Wrong credentials information", null, null);
        } else if (admin.getStatus().equals(AdminStatus.INACTIVE)) {
            log.info("Admin found in database with username with inactive status:" + admin.getFullname());
            responseObject = new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), "Account has been locked", null, null);
        } else if (admin.getStatus().equals(AdminStatus.ACTIVE)) {
            log.info("Admin found in database with username with active status:" + admin.getFullname());
            UserPrinciple userPrinciple = UserPrinciple.adminBuild(admin);
            String accessToken = jwtService.createAccessToken(userPrinciple);
            String refreshToken = jwtService.createRefreshToken(userPrinciple);
            tokenResponse = new TokenResponse(accessToken, refreshToken);
            userPrinciple.setTokenResponse(tokenResponse);
            responseObject = new ResponseObject(HttpStatus.ACCEPTED.toString(), "Login successfully", null, userPrinciple);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseObject);
    }


}
