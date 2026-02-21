package com.sjcapstone.tandanji.ai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiService {

    private final AiClient aiClient;

    public AiPredictResponseDto predict(MultipartFile file) {
        try {
            AiPredictResponseDto result = aiClient.predict(file);

            log.info("[AI] SUCCESS filename={} result={}",
                    file.getOriginalFilename(),
                    result);

            return result;

        } catch (Exception e) {

            log.error("[AI] FAIL filename={} error={} message={}",
                    file.getOriginalFilename(),
                    e.getClass().getSimpleName(),
                    e.getMessage(),
                    e);

            // ❌ [삭제] throw e;
            // 이유: 예외를 던지면 컨트롤러까지 올라가서 500이 뜸.
            // 팀장 요구 "AI 서버 죽여도 Spring 안 죽음" = 여기서 예외를 밖으로 보내지 말아야 함.

            // ✅ [추가] record는 기본 생성자/Setter가 없어서 "new AiPredictResponseDto()" + setXXX 불가
            // 그래서 record의 생성자(모든 필드 받는 생성자)를 직접 호출해서 fallback 응답을 만든다.
            return new AiPredictResponseDto(
                    file.getOriginalFilename(), // filename
                    null,                       // label (AI 결과 없음)
                    0.0                         // confidence (임시값)
            );
        }
    }
}
