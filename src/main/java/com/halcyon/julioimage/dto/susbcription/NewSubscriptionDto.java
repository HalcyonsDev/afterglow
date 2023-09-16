package com.halcyon.julioimage.dto.susbcription;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewSubscriptionDto {
    @Email
    private String targetEmail;
}