package com.halcyon.afterglow.dto.post;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewPostDto {
    @Size(min = 1, max = 50, message = "Name must be more than 1 character and less than 50 characters.")
    private String name;

    @Size(min = 3, max = 64, message = "Description must be more than 3 characters and less than 64 characters.")
    private String description;
}