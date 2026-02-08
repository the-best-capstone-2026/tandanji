package com.sjcapstone.tandanji.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    // Swagger 접속: http://localhost:8080/swagger-ui.html

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tandanji API")
                        .description("Food Image Classification API")
                        .version("v1.0"));
    }
}
