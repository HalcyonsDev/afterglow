package com.halcyon.afterglow.util;

import com.halcyon.afterglow.security.JwtAuthentication;
import io.jsonwebtoken.Claims;

public class JwtUtil {
    public static JwtAuthentication getAuthentication(Claims claims) {
        final JwtAuthentication jwtAuthInfo = new JwtAuthentication();
        jwtAuthInfo.setEmail(claims.getSubject());

        return jwtAuthInfo;
    }
}