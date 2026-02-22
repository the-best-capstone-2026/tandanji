package com.sjcapstone.tandanji.domain.classification.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassificationImageMeta {

    @Id
    private Long requestId; // PK

    @MapsId // ClassificationRequest의 PK를 공유
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private ClassificationRequest request;

    private String imageFilename;
    private String imageContentType;
    private Long imageSizeBytes;

    private String storageProvider; // LOCAL, S3 등

    @Column(columnDefinition = "TEXT")
    private String storagePath;

    @Column(columnDefinition = "TEXT")
    private String storageUrl;
}