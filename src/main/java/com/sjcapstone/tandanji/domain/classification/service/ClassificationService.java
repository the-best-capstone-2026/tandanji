package com.sjcapstone.tandanji.domain.classification.service;

import com.sjcapstone.tandanji.domain.classification.dto.ClassificationResponseDto;
import com.sjcapstone.tandanji.domain.classification.dto.ClassificationSaveRequestDto;
import com.sjcapstone.tandanji.domain.classification.entity.ClassificationImageMeta;
import com.sjcapstone.tandanji.domain.classification.entity.ClassificationRequest;
import com.sjcapstone.tandanji.domain.classification.entity.ClassificationResult;
import com.sjcapstone.tandanji.domain.classification.repository.ClassificationImageMetaRepository;
import com.sjcapstone.tandanji.domain.classification.repository.ClassificationRequestRepository;
import com.sjcapstone.tandanji.domain.classification.repository.ClassificationResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClassificationService {

    private final ClassificationRequestRepository requestRepository;
    private final ClassificationImageMetaRepository imageMetaRepository;
    private final ClassificationResultRepository resultRepository;

    /**
     * [저장] AI 분류 요청 정보를 3개의 테이블에 분산 저장합니다.
     */
    @Transactional
    public Long saveClassificationFullLog(ClassificationRequest request,
                                          ClassificationImageMeta imageMeta,
                                          List<ClassificationResult> results) {
        requestRepository.save(request);      // 1. Request 저장
        imageMetaRepository.save(imageMeta);   // 2. Meta 저장
        resultRepository.saveAll(results);     // 3. Results 저장
        return request.getId();
    }

    /**
     * [조회] DB에 저장된 전체 히스토리를 DTO 형태로 변환하여 반환합니다.
     */
    public List<ClassificationResponseDto> getClassificationHistory() {
        return requestRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Entity를 Response DTO로 매핑하는 내부 로직입니다.
     */
    private ClassificationResponseDto convertToResponseDto(ClassificationRequest request) {
        return ClassificationResponseDto.builder()
                .requestId(request.getId())
                .requestedAt(request.getRequestedAt())
                .status(request.getStatus())
                .storageUrl(request.getImageMeta() != null ? request.getImageMeta().getStorageUrl() : null)
                .aiResults(request.getResults().stream()
                        .map(r -> ClassificationResponseDto.ResultDto.builder()
                                .label(r.getAiLabel())
                                .confidence(r.getAiConfidence() != null ? r.getAiConfidence().doubleValue() : 0.0)
                                .modelVersion(r.getModelVersion())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
    // ClassificationService.java에 추가
    @Transactional
    public Long saveFromDto(ClassificationSaveRequestDto dto) {
        // 1. Request 생성
        ClassificationRequest request = ClassificationRequest.builder()
                .status("SUCCESS") // Dto에 status 필드가 없다면 기본값 설정
                .requestedAt(LocalDateTime.now())
                .attemptCount(1)
                .build();

        // 2. ImageMeta 생성 (필드명 매치 필수)
        ClassificationImageMeta imageMeta = ClassificationImageMeta.builder()
                .storageUrl(dto.getStorageUrl())
                .imageFilename(dto.getFileName()) // dto.getFileName() 사용
                .request(request) // .classificationRequest가 아님
                .build();

        // 3. Results 변환
        List<ClassificationResult> results = dto.getAiResults().stream()
                .map(resultDto -> ClassificationResult.builder()
                        .aiLabel(resultDto.getLabel())
                        .aiConfidence(java.math.BigDecimal.valueOf(resultDto.getConfidence()))
                        .modelVersion(resultDto.getModelVersion())
                        .latencyMs(resultDto.getLatencyMs())
                        .request(request) // 필드명 확인
                        .build())
                .collect(Collectors.toList());

        return saveClassificationFullLog(request, imageMeta, results);
    }
}