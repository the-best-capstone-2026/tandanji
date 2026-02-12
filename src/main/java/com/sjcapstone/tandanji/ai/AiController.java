package com.sjcapstone.tandanji.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    // 테스트: /ai/test/predict-test
    // 실서비스: /ai/predict
    @PostMapping(value = "/predict", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> predict(@RequestPart("file") MultipartFile file) {
        try {
            Map result = aiService.predict(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "error", e.getClass().getName(),
                    "message", String.valueOf(e.getMessage())
            ));
        }
    }
}
