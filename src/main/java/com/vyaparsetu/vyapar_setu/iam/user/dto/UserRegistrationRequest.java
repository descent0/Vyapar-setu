package com.vyaparsetu.vyapar_setu.iam.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import com.vyaparsetu.vyapar_setu.iam.business.entity.BusinessType;

public record UserRegistrationRequest(

        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 100)
        String name,

        @NotBlank(message = "Phone number is required")
        @Pattern(
                regexp = "\\+?[1-9]\\d{7,14}",
                message = "Invalid phone number"
        )
        String phoneNumber,

        @Email(message = "Invalid email address")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 72)
        String password,

        @NotBlank(message = "Business name is required")
        @Size(max = 150)
        String businessName,

        @NotNull(message = "Business type is required")
        BusinessType businessType

) {
}