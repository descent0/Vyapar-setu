package com.vyaparsetu.vyapar_setu.iam.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, length = 16)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role platformRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;


    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();

        if (platformRole == null) {
            platformRole = Role.USER;
        }
        if (status == null) {
            status = UserStatus.ACTIVE;
        }
    }
}