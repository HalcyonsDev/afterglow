package com.halcyon.afterglow.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    @Email(message = "Email is not valid.")
    private String email;

    @Size(min = 1, max = 50, message = "Firstname must be more than 1 character and less than 50 characters.")
    private String firstname;

    @Size(min = 1, max = 50, message = "Lastname must be more than 1 character and less than 50 characters.")
    private String lastname;

    @Size(min = 4, message = "Password must be more than 6 characters.")
    private String password;
}