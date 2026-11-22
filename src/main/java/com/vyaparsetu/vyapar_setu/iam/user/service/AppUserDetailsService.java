package com.vyaparsetu.vyapar_setu.iam.user.service;

import com.vyaparsetu.vyapar_setu.iam.user.entity.User;
import com.vyaparsetu.vyapar_setu.iam.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid phone number or password"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getPhoneNumber())
                .password(user.getPasswordHash())
                .roles(user.getPlatformRole().name())
                .disabled(user.getStatus() != com.vyaparsetu.vyapar_setu.iam.user.entity.UserStatus.ACTIVE)
                .build();
    }
}
