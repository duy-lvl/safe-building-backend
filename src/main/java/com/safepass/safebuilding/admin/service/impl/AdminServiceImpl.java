package com.safepass.safebuilding.admin.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.safepass.safebuilding.admin.entity.Admin;
import com.safepass.safebuilding.admin.repository.AdminRepository;
import com.safepass.safebuilding.admin.service.AdminService;
import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.common.meta.AdminStatus;
import com.safepass.safebuilding.common.meta.LoginAuthorities;
import com.safepass.safebuilding.common.security.jwt.userprincipal.UserPrinciple;
import com.safepass.safebuilding.common.service.RefreshTokenService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.safepass.safebuilding.admin.entity.Admin;
import com.safepass.safebuilding.admin.repository.AdminRepository;
import com.safepass.safebuilding.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@Log4j2
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    private final ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.secret}")
    static String secret="safe_building";

    @Value("${app.jwtExpirationMs}")
    static int accessTokenDuration=1800000;

    @Value("${app.jwtRefreshExpirationMs}")
    static int refreshTokenDuration= 86400000;
    public AdminServiceImpl(AdminRepository adminRepository, ModelMapper modelMapper) {
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<ResponseObject> login(HttpServletResponse response, HttpServletRequest request, String phone, String password) {
//        password = passwordEncoder.encode(password);
//        if(passwordEncoder.matches(password, "$2a$10$6k1B72.CHyYVD2mFxI51tu8l9Io6hWTmPq9ypugSQ2BmeydOCNWja")){
//            log.info(password);
//        }
        Admin admin = adminRepository.findAdminByPhone(phone);
//        if(admin!=null ){
//            if(!passwordEncoder.matches(password, admin.getPassword())){
//                admin = null;
//            }
//        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(phone+"-Admin", password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        UserPrinciple user = null ;
        ResponseObject responseObject = null;
        if(admin == null){
            log.error("Wrong credentials information");
            responseObject = new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), "Wrong credentials information", null, null );
        } else if(admin.getStatus().equals(AdminStatus.INACTIVE)) {
            log.info("Admin found in database with username with inactive status:"+admin.getFullname());
            responseObject = new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), "Account has been locked", null, null );
        } else if(admin.getStatus().equals(AdminStatus.ACTIVE)) {
            log.info("Admin found in database with username with active status:" + admin.getFullname());
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(LoginAuthorities.ADMIN.toString()));
            user = UserPrinciple.adminBuild(admin);
            responseObject = new ResponseObject(HttpStatus.ACCEPTED.toString(), "Login successfully", null, userPrinciple );
        }
//        RefreshTokenService.createJwt(response, request, userPrinciple);
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        String accessToken = JWT.create()
                .withSubject(userPrinciple.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+accessTokenDuration))
//                .withIssuer(request.getRequestURI().toString())
                .withClaim("roles", userPrinciple.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String refreshToken = JWT.create()
                .withSubject(userPrinciple.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+refreshTokenDuration))
//                .withIssuer(request.getRequestURI().toString())
                .sign(algorithm);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access_token",
                accessToken);
        responseHeaders.set("Refresh_token", refreshToken);
        return ResponseEntity.status(HttpStatus.OK)
                .headers(responseHeaders)
                .body(responseObject);
    }

    @Override
    public ResponseEntity<ResponseObject> loginWithEmail(HttpServletResponse response, HttpServletRequest request, String email) {
        Admin admin = adminRepository.findAdminByEmail(email);
//        if(admin!=null ){
//            if(!passwordEncoder.matches(password, admin.getPassword())){
//                admin = null;
//            }
//        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(admin.getPhone()+"-Admin", admin.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        UserPrinciple user = null ;
        ResponseObject responseObject = null;
        if(admin == null){
            log.error("Wrong credentials information");
            responseObject = new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), "Wrong credentials information", null, null );
        } else if(admin.getStatus().equals(AdminStatus.INACTIVE)) {
            log.info("Admin found in database with username with inactive status:"+admin.getFullname());
            responseObject = new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), "Account has been locked", null, null );
        } else if(admin.getStatus().equals(AdminStatus.ACTIVE)) {
            log.info("Admin found in database with username with active status:" + admin.getFullname());
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(LoginAuthorities.ADMIN.toString()));
            user = UserPrinciple.adminBuild(admin);
            responseObject = new ResponseObject(HttpStatus.ACCEPTED.toString(), "Login successfully", null, userPrinciple );
        }
//        RefreshTokenService.createJwt(response, request, userPrinciple);
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        String accessToken = JWT.create()
                .withSubject(userPrinciple.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+accessTokenDuration))
//                .withIssuer(request.getRequestURI().toString())
                .withClaim("roles", userPrinciple.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
        String refreshToken = JWT.create()
                .withSubject(userPrinciple.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+refreshTokenDuration))
//                .withIssuer(request.getRequestURI().toString())
                .sign(algorithm);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access_token",
                accessToken);
        responseHeaders.set("Refresh_token", refreshToken);
        return ResponseEntity.status(HttpStatus.OK)
                .headers(responseHeaders)
                .body(responseObject);
    }


}
