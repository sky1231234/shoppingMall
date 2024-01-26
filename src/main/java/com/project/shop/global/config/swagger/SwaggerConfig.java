package com.project.shop.global.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .components(components())
                .info(apiInfo())
                .addSecurityItem(securityRequirement());
//                .security(Arrays.asList(securityRequirement()));
    }

    private Info apiInfo() {
        return new Info()
                .title("swagger 테스트")
                .description("Springdoc - Swagger UI")
                .version("1.0.0");
    }

    private Components components() {
        return new Components()
                .addSecuritySchemes("Authorization",
                        //security 스키마
                        new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                            .in(SecurityScheme.In.HEADER)
                            .name("Authorization")
                );
    }

    private SecurityRequirement securityRequirement(){
        //security 요청
        return new SecurityRequirement()
                .addList("Authorization");
    }
}