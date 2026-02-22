package com.sjcapstone.tandanji.domain.classification.controller;

import com.sjcapstone.tandanji.domain.classification.dto.ClassificationResponseDto;
import com.sjcapstone.tandanji.domain.classification.dto.ClassificationSaveRequestDto;
import com.sjcapstone.tandanji.domain.classification.service.ClassificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Classification", description = "음식 이미지 분류 및 이력 관리 API")
@RestController
@RequestMapping("/api/classification")
@RequiredArgsConstructor
public class ClassificationController {

    private final ClassificationService classificationService;

    /**
     * 전체 분류 히스토리를 조회합니다. (2주차 목표)
     */
    @GetMapping("/history")
    public ResponseEntity<List<ClassificationResponseDto>> getHistory() {
        List<ClassificationResponseDto> history = classificationService.getClassificationHistory();
        return ResponseEntity.ok(history);
    }
    // ClassificationController.java에 추가
    @PostMapping("/save")
    public ResponseEntity<Long> saveClassification(@RequestBody ClassificationSaveRequestDto dto) {
        Long savedId = classificationService.saveFromDto(dto);
        return ResponseEntity.ok(savedId);
    }

    // AI 예측 결과 저장 API (실제 모델 연동 후 호출되는 엔드포인트)
    // @PostMapping("/save") ...
}