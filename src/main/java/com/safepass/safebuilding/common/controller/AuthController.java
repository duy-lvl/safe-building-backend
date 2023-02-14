//package com.safepass.safebuilding.common.controller;
//
//import com.safepass.safebuilding.common.dto.RefreshToken;
//import com.safepass.safebuilding.common.exception.TokenRefreshException;
//import com.safepass.safebuilding.common.meta.LoginAuthorities;
//import com.safepass.safebuilding.common.security.jwt.JwtUtils;
//import com.safepass.safebuilding.common.security.jwt.payload.response.JwtResponse;
//import com.safepass.safebuilding.common.security.jwt.payload.response.TokenRefreshResponse;
//import com.safepass.safebuilding.common.security.jwt.payload.resquest.TokenRefreshRequest;
//import com.safepass.safebuilding.common.security.jwt.userprincipal.UserPrinciple;
//import com.safepass.safebuilding.common.service.RefreshTokenService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/v1/api/auth")
//public class AuthController {
//
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    JwtUtils jwtUtils;
//
//    @Autowired
//    RefreshTokenService refreshTokenService;
//
//
//    @PostMapping("/web/signin")
//    public ResponseEntity<?> signInWeb(@Valid @RequestBody String phoneNumber, @RequestBody String password) {
//
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(phoneNumber, password));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();
//
//        String jwt = jwtUtils.generateJwtToken(userDetails);
//
//        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
//                .collect(Collectors.toList());
//
//        com.safepass.safebuilding.common.dto.RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId(), LoginAuthorities.ADMIN);
//
//        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
//                userDetails.getUsername(), userDetails.getEmail(), roles));
//    }
//
//    @PostMapping("/web/gmail/signin")
//    public ResponseEntity<?> signInWebWithGmail(@Valid @RequestBody String email, @RequestBody String password) {
//
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();
//
//        String jwt = jwtUtils.generateJwtToken(userDetails);
//
//        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
//                .collect(Collectors.toList());
//
//        com.safepass.safebuilding.common.dto.RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId(), LoginAuthorities.ADMIN);
//
//        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
//                userDetails.getUsername(), userDetails.getEmail(), roles));
//    }
//
//    @PostMapping("/mobile/signin")
//    public ResponseEntity<?> signInMobile(@Valid @RequestBody String phoneNumber, @RequestBody String password) {
//
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(phoneNumber, password));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();
//
//        String jwt = jwtUtils.generateJwtToken(userDetails);
//
//        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
//                .collect(Collectors.toList());
//
//        com.safepass.safebuilding.common.dto.RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId(), LoginAuthorities.CUSTOMER);
//
//        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
//                userDetails.getUsername(), userDetails.getEmail(), roles));
//    }
//
//    @PostMapping("/mobile/gmail/signin")
//    public ResponseEntity<?> signInMobileWithEmail(@Valid @RequestBody String phoneNumber, @RequestBody String password) {
//
//        Authentication authentication = authenticationManager
//                .authenticate(new UsernamePasswordAuthenticationToken(phoneNumber, password));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        UserPrinciple userDetails = (UserPrinciple) authentication.getPrincipal();
//
//        String jwt = jwtUtils.generateJwtToken(userDetails);
//
//        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
//                .collect(Collectors.toList());
//
//        com.safepass.safebuilding.common.dto.RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId(), LoginAuthorities.CUSTOMER);
//
//        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
//                userDetails.getUsername(), userDetails.getEmail(), roles));
//    }
//
//    @PostMapping("/web/refreshtoken")
//    public ResponseEntity<TokenRefreshResponse> refreshTokenWeb(@Valid @RequestBody TokenRefreshRequest request) {
//        String requestRefreshToken = request.getRefreshToken();
//
//        return refreshTokenService.findByToken(requestRefreshToken)
//                .map(refreshTokenService::verifyExpiration)
//                .map(RefreshToken::getAdmin)
//                .map(user -> {
//                    String token = jwtUtils.generateTokenFromUsername(user.getPhone());
//                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
//                })
//                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
//                        "Refresh token is not in database!"));
//    }
//    @PostMapping("/mobile/refreshtoken")
//    public ResponseEntity<TokenRefreshResponse> refreshTokenMobile(@Valid @RequestBody TokenRefreshRequest request) {
//        String requestRefreshToken = request.getRefreshToken();
//
//        return refreshTokenService.findByToken(requestRefreshToken)
//                .map(refreshTokenService::verifyExpiration)
//                .map(RefreshToken::getAdmin)
//                .map(user -> {
//                    String token = jwtUtils.generateTokenFromUsername(user.getPhone());
//                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
//                })
//                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
//                        "Refresh token is not in database!"));
//    }
//}
