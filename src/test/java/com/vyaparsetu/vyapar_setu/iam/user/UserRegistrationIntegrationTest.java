package com.vyaparsetu.vyapar_setu.iam.user;

import com.vyaparsetu.vyapar_setu.iam.business.entity.BusinessType;
import com.vyaparsetu.vyapar_setu.iam.business.repo.BusinessMembershipRepo;
import com.vyaparsetu.vyapar_setu.iam.business.repo.BusinessRepo;
import com.vyaparsetu.vyapar_setu.iam.user.dto.UserRegistrationRequest;
import com.vyaparsetu.vyapar_setu.iam.user.dto.UserResponse;
import com.vyaparsetu.vyapar_setu.iam.user.repo.UserRepo;
import com.vyaparsetu.vyapar_setu.iam.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserRegistrationIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private BusinessRepo businessRepository;

    @Autowired
    private BusinessMembershipRepo membershipRepository;

    @Test
    void registersUserWithBusinessAndMembership() {
        UserRegistrationRequest request = new UserRegistrationRequest(
                "Test Owner", "+919876543210", "owner@example.com",
                "password123", "Test Kirana", BusinessType.RETAILER
        );

        UserResponse response = userService.registerUser(request);

        assertNotNull(response.id());
        assertEquals("Test Owner", response.name());
        assertTrue(userRepository.findById(response.id()).orElseThrow()
                .getPasswordHash().startsWith("$2"));
        assertEquals(1, businessRepository.count());
        assertEquals(1, membershipRepository.count());
    }
}
