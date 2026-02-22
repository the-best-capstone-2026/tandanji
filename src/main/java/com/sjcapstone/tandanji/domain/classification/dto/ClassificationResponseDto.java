package com.sjcapstone.tandanji.domain.classification.dto;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ClassificationResponseDto {
    private Long requestId;
    private LocalDateTime requestedAt;
    private String status;
    private String imageOriginalName;
    private String storageUrl;
    private List<ResultDto> aiResults;

    @Getter
    @Builder
    public static class ResultDto {
        private String label;
        private Double confidence;
        private String modelVersion;
    }
}