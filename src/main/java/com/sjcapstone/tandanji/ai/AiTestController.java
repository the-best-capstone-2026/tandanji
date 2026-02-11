package com.sjcapstone.tandanji.ai;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@RestController
@RequestMapping("/ai")
public class AiTestController {

    private final WebClient aiWebClient;

    public AiTestController(WebClient aiWebClient) {
        this.aiWebClient = aiWebClient;
    }

    @PostMapping(value = "/predict-test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> predictTest(@RequestPart("file") MultipartFile file) {
        try {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("file", file.getResource());

            Map result = aiWebClient.post()
                    .uri("/predict")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            System.out.println("[AI] SUCCESS filename=" + file.getOriginalFilename() + " result=" + result);

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            System.out.println("[AI] FAIL filename=" + file.getOriginalFilename() + " error=" + e.getClass().getSimpleName() + " message=" + e.getMessage());

            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of(
                    "error", e.getClass().getName(),
                    "message", String.valueOf(e.getMessage())
            ));
        }
    }
}
