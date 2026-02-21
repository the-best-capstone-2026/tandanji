package com.sjcapstone.tandanji.ai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class AiClient {

    private final WebClient aiWebClient;

    public AiPredictResponseDto predict(MultipartFile file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", file.getResource());

        return aiWebClient.post()
                .uri("/predict")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(AiPredictResponseDto.class)
                .retry(1) // ✅ 실패하면 1번만 재시도 (MVP용)
                .block();
    }

    // ✅ /api/health에서 AI 서버 살아있는지 확인용
    public void checkHealth() {
        aiWebClient.get()
                .uri("/docs") // FastAPI가 켜져 있으면 보통 항상 응답하는 경로
                .retrieve()
                .toBodilessEntity() // 응답 바디 필요 없고 상태코드만 확인
                .block(); // 정상 응답이면 성공, 죽었으면 예외 발생
    }
}
