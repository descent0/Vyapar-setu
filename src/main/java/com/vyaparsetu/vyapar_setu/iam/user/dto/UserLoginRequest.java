package com.vyaparsetu.vyapar_setu.iam.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(

        @NotBlank
        String phoneNumber,

        @NotBlank
        String password

) {
}
