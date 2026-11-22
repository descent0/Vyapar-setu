package com.vyaparsetu.vyapar_setu.iam.user.service;

import com.vyaparsetu.vyapar_setu.iam.user.dto.UserRegistrationRequest;
import com.vyaparsetu.vyapar_setu.iam.user.dto.UserResponse;
import com.vyaparsetu.vyapar_setu.iam.business.entity.Business;
import com.vyaparsetu.vyapar_setu.iam.business.entity.BusinessMembership;
import com.vyaparsetu.vyapar_setu.iam.business.entity.MembershipRole;
import com.vyaparsetu.vyapar_setu.iam.business.repo.BusinessMembershipRepo;
import com.vyaparsetu.vyapar_setu.iam.business.repo.BusinessRepo;
import com.vyaparsetu.vyapar_setu.iam.user.entity.Role;
import com.vyaparsetu.vyapar_setu.iam.user.entity.User;
import com.vyaparsetu.vyapar_setu.iam.user.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BusinessRepo businessRepository;
    private final BusinessMembershipRepo membershipRepository;


    @Transactional
    public UserResponse registerUser(UserRegistrationRequest request) {

        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Phone number already registered"
            );
        }

        if (request.email() != null &&
                !request.email().isBlank() &&
                userRepository.existsByEmail(request.email())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already registered"
            );
        }

        User user = new User();

        user.setName(request.name());
        user.setPhoneNumber(request.phoneNumber());
        user.setEmail(request.email());
        user.setPasswordHash(
                passwordEncoder.encode(request.password())
        );
        user.setPlatformRole(Role.USER);

        User savedUser = userRepository.save(user);

        Business business = new Business();
        business.setBusinessName(request.businessName());
        business.setBusinessType(request.businessType());
        Business savedBusiness = businessRepository.save(business);

        BusinessMembership membership = new BusinessMembership();
        membership.setUser(savedUser);
        membership.setBusiness(savedBusiness);
        membership.setMembershipRole(MembershipRole.OWNER);
        membershipRepository.save(membership);

        return mapToResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));

        return mapToResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found"
                ));
        return mapToResponse(user);
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getPlatformRole(),
                user.getStatus(),
                user.getCreatedAt()
        );
    }
}