package com.intern.authsystem.infrastructure.swagger;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AuthSystem API")
                        .version("v1.0")
                        .description("JWT 인증/인가 과제용 API 명세서"));
    }
}