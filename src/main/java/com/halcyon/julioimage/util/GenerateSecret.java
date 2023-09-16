package com.halcyon.julioimage.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

public class GenerateSecret {
    public static void main(String[] args) {
        System.out.println(generateToken());
        System.out.println(generateToken());
    }

    private static String generateToken() {
        return Encoders.BASE64.encode(Keys.secretKeyFor(SignatureAlgorithm.HS512).getEncoded());
    }
}