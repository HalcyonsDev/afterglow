package com.halcyon.julioimage.service.auth;

import com.halcyon.julioimage.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    private final SecretKey accessToken;
    private final SecretKey refreshToken;

    public JwtProvider(
            @Value("${jwt.secret.access}") String accessToken,
            @Value("${jwt.secret.refresh}") String refreshToken
    ) {
        this.accessToken = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessToken));
        this.refreshToken = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshToken));
    }

    public String generateToken(User user, boolean isRefresh) {
        return generateToken(new HashMap<>(), user, isRefresh);
    }

    public String generateToken(Map<String, Object> extraClaims, User user, boolean isRefresh) {
        LocalDateTime now = LocalDateTime.now();

        Instant expirationInstant = isRefresh ?
                now.plusDays(31).atZone(ZoneId.systemDefault()).toInstant() :
                now.plusDays(7).atZone(ZoneId.systemDefault()).toInstant();

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(expirationInstant))
                .signWith(isRefresh ? refreshToken : accessToken)
                .compact();
    }

    public Claims extractAccessClaims(String jwtToken) {
        return extractAllClaims(jwtToken, accessToken);
    }

    public Claims extractRefreshClaims(String jwtToken) {
        return extractAllClaims(jwtToken, refreshToken);
    }

    private Claims extractAllClaims(String jwtToken, Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public boolean isValidAccessToken(String jwtToken) {
        return isValidToken(jwtToken, accessToken);
    }

    public boolean isValidRefreshToken(String jwtToken) {
        return isValidToken(jwtToken, refreshToken);
    }

    private boolean isValidToken(String jwtToken, Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(jwtToken);

            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}