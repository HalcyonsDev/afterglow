package com.halcyon.julioimage.service.token;

import com.halcyon.julioimage.model.Token;
import com.halcyon.julioimage.repository.ITokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final ITokenRepository tokenRepository;

    public Token create(Token token) {
        return tokenRepository.save(token);
    }

    public Token findByValue(String value) {
        return tokenRepository.findByValue(value)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Token with this value not found."));
    }
}