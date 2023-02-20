package com.safepass.safebuilding.common.jwt.service.serviceImpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.safepass.safebuilding.common.jwt.service.JwtService;
import com.safepass.safebuilding.common.security.user.UserPrinciple;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Log4j2
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secretKey}")
    private String secret;

    @Value("${jwt.expirationMs}")
    private int accessTokenDuration;

    @Value("${jwt.refreshExpirationMs}")
    private int refreshTokenDuration;

    @Value("${jwt.issuer}")
    private String issuer;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Algorithm algorithm;

    /**
     * create access token
     *
     * @param userPrinciple
     * @return String
     */
    @Override
    public String createAccessToken(UserPrinciple userPrinciple) {
        algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.create()
                .withSubject(userPrinciple.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenDuration))
                .withIssuer(issuer)
                .withClaim("roles", userPrinciple.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    /**
     * create refresh token
     *
     * @param userPrinciple
     * @return String
     */
    @Override
    public String createRefreshToken(UserPrinciple userPrinciple) {
        algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.create()
                .withSubject(userPrinciple.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenDuration))
                .withIssuer(issuer)
                .sign(algorithm);
    }


//    public String generateJwtToken(UserPrinciple userPrincipal) {
//        return generateTokenFromUsername(userPrincipal.getUsername());
//    }
//
//    public String generateTokenFromUsername(String username) {
//        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }
//
//    public String getUserNameFromJwtToken(String token) {
//        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
//    }
//
//    public boolean validateJwtToken(String authToken) {
//        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//            return true;
//        } catch (SignatureException e) {
//            log.error("Invalid JWT signature: {}", e.getMessage());
//        } catch (MalformedJwtException e) {
//            log.error("Invalid JWT token: {}", e.getMessage());
//        } catch (ExpiredJwtException e) {
//            log.error("JWT token is expired: {}", e.getMessage());
//        } catch (UnsupportedJwtException e) {
//            log.error("JWT token is unsupported: {}", e.getMessage());
//        } catch (IllegalArgumentException e) {
//            log.error("JWT claims string is empty: {}", e.getMessage());
//        }
//
//        return false;
//    }
}
