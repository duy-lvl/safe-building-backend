package com.safepass.safebuilding.common.jwt.service;

import com.safepass.safebuilding.common.jwt.entity.response.JwtResponse;
import com.safepass.safebuilding.common.security.user.UserPrinciple;

public interface JwtService {
    String createAccessToken(UserPrinciple userPrinciple);
    String createRefreshToken(UserPrinciple userPrinciple);
}
