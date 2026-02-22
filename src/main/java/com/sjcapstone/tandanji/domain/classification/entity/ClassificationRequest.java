package com.sjcapstone.tandanji.domain.classification.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassificationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // BIGSERIAL (PK)

    @Column(nullable = false, updatable = false)
    private LocalDateTime requestedAt = LocalDateTime.now(); // TIMESTAMP, DEFAULT NOW()

    @Column(length = 20, nullable = false)
    private String status = "SUCCESS"; // VARCHAR(20), DEFAULT 'SUCCESS'

    @Column(nullable = false)
    private Integer attemptCount = 1; // INTEGER, DEFAULT 1

    @Column(columnDefinition = "TEXT")
    private String errorMessage; // TEXT, NULL 가능

    // 양방향 연관관계 (선택 사항이나 관리상 편리함)
    @OneToOne(mappedBy = "request", cascade = CascadeType.ALL)
    private ClassificationImageMeta imageMeta;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<ClassificationResult> results = new ArrayList<>();


}