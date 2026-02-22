package com.sjcapstone.tandanji.domain.classification.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class ClassificationSaveRequestDto {
    private String status; // 필드 추가 필요

    // 이미지 메타 데이터 정보
    private String fileName;
    private String contentType;
    private Long fileSize;
    private String storageProvider; // LOCAL, S3 등
    private String storagePath;
    private String storageUrl;

    // AI 분석 결과 목록 (여러 개의 결과가 나올 수 있는 1:N 구조 반영)
    private List<ResultRequestDto> aiResults;

    @Getter
    @NoArgsConstructor
    public static class ResultRequestDto {
        private String label;
        private Double confidence;
        private String modelVersion;
        private Integer latencyMs;
    }
}