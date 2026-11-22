package com.vyaparsetu.vyapar_setu.iam.business.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "businesses")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String businessName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BusinessType businessType;

    @Column(length = 500)
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus verificationStatus;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
        if (verificationStatus == null) {
            verificationStatus = VerificationStatus.PENDING;
        }
    }
}
