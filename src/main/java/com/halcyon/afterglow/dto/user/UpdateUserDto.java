package com.halcyon.afterglow.dto.user;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    @Size(min = 1, max = 50, message = "Firstname must be more than 1 character and less than 50 characters.")
    private String firstname;

    @Size(min = 1, max = 50, message = "Lastname must be more than 1 character and less than 50 characters.")
    private String lastname;

    @Size(max = 40, message = "Description must be less than 40 characters.")
    private String description;
}