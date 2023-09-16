package com.halcyon.julioimage.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private final String TYPE = "Bearer";
    private String accessToken;
    private String refreshToken;
}