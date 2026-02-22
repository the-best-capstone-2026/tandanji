package com.sjcapstone.tandanji.domain.classification.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassificationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // BIGSERIAL (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private ClassificationRequest request; // FK

    private String aiLabel;

    @Column(precision = 6, scale = 5)
    private BigDecimal aiConfidence; // DECIMAL(6,5) 반영

    private String modelVersion;
    private Integer latencyMs;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}