package com.halcyon.afterglow.service.auth;

import com.halcyon.afterglow.dto.auth.SignUpDto;
import com.halcyon.afterglow.model.Token;
import com.halcyon.afterglow.model.User;
import com.halcyon.afterglow.service.token.TokenService;
import com.halcyon.afterglow.service.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {
    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthService authService;

    private static User user;

    @BeforeAll
    public static void BeforeAll() {
        user = User.builder()
                .email("test_user@gmail.com")
                .firstname("test_firstname")
                .lastname("test_lastname")
                .password("test_password123")
                .build();
    }

    @Test
    public void AuthService_SignUp_ReturnsAuthResponse() {
        Token token = new Token();
        token.setValue("TOKEN.TOKEN.TOKEN");

        SignUpDto dto = new SignUpDto(
                user.getEmail(),
                user.getFirstname(),
                user.getLastname(),
                user.getPassword()
        );

        when(userService.create(Mockito.any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("HASHED_PASSWORD");
        when(jwtProvider.generateToken(Mockito.any(User.class), Mockito.anyBoolean())).thenReturn("TOKEN.TOKEN.TOKEN");

    }
}