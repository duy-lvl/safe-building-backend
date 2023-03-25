//package com.safepass.safebuilding.common.jwt.service;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.safepass.safebuilding.admin.repository.AdminRepository;
//import com.safepass.safebuilding.common.dto.RefreshToken;
//import com.safepass.safebuilding.common.exception.TokenRefreshException;
//import com.safepass.safebuilding.common.meta.LoginAuthorities;
//import com.safepass.safebuilding.common.repository.RefreshTokenRepository;
//import com.safepass.safebuilding.common.security.user.UserPrinciple;
//import com.safepass.safebuilding.customer.repository.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.transaction.Transactional;
//import java.time.Instant;
//import java.util.Date;
//import java.util.Optional;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//public class RefreshTokenService {
//    @Value("${app.jwtRefreshExpirationMs}")
//    private Long refreshTokenDurationMs;
//
//    @Value("${app.secret}")
//    static String secret="safe_building";
//
////    @Value("${app.jwtExpirationMs}")
//    static int accessTokenDuration=1800000;
//
//    @Value("${app.jwtRefreshExpirationMs}")
//    static int refreshTokenDuration= 86400000;
//    @Autowired
//    private RefreshTokenRepository refreshTokenRepository;
//
//    @Autowired
//    private AdminRepository adminRepository;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//
//
//    public Optional<RefreshToken> findByToken(String token) {
//        return refreshTokenRepository.findByToken(token);
//    }
//
//    public RefreshToken createRefreshToken(UUID id, LoginAuthorities loginAuthorities) {
//        RefreshToken refreshToken = new RefreshToken();
//        if(LoginAuthorities.ADMIN.equals(loginAuthorities)){
//            refreshToken.setAdmin(adminRepository.findById(id).get());
//            refreshToken.setLoginAuthorities(LoginAuthorities.ADMIN);
//        }else if(LoginAuthorities.CUSTOMER.equals(loginAuthorities)){
//            refreshToken.setCustomer(customerRepository.findById(id).get());
//            refreshToken.setLoginAuthorities(LoginAuthorities.CUSTOMER);
//        }
//        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
//        refreshToken.setToken(UUID.randomUUID().toString());
//        refreshToken = refreshTokenRepository.save(refreshToken);
//        return refreshToken;
//    }
//
//    public RefreshToken verifyExpiration(RefreshToken token) {
//        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
//            refreshTokenRepository.delete(token);
//            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
//        }
//
//        return token;
//    }
//
//    @Transactional
//    public int deleteByUserId(UUID id, LoginAuthorities loginAuthorities) {
//        if(LoginAuthorities.ADMIN.equals(loginAuthorities)){
//            return refreshTokenRepository.deleteByAdmin(adminRepository.findById(id).get());
//        }else if(LoginAuthorities.CUSTOMER.equals(loginAuthorities)){
//            return refreshTokenRepository.deleteByCustomer(customerRepository.findById(id).get());
//        }
//        return 0;
//    }
//
//    public static void createJwt(HttpServletResponse response, HttpServletRequest request, UserPrinciple user){
////        UserPrinciple user = (UserPrinciple) authentication.getPrincipal();
//        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
//        String accessToken = JWT.create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis()+accessTokenDuration))
////                .withIssuer(request.getRequestURI().toString())
//                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                .sign(algorithm);
//        String refreshToken = JWT.create()
//                .withSubject(user.getUsername())
//                .withExpiresAt(new Date(System.currentTimeMillis()+refreshTokenDuration))
////                .withIssuer(request.getRequestURI().toString())
//                .sign(algorithm);
//        response.setHeader("access_token",accessToken);
//        response.setHeader("refresh_token",refreshToken);
//    }
//}
