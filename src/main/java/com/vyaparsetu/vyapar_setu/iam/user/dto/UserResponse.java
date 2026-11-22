package com.vyaparsetu.vyapar_setu.iam.user.dto;

import com.vyaparsetu.vyapar_setu.iam.user.entity.Role;
import com.vyaparsetu.vyapar_setu.iam.user.entity.UserStatus;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String name,
        String phoneNumber,
        String email,
        Role platformRole,
        UserStatus status,
        LocalDateTime createdAt
) {
}