package com.gearx.common.config;

import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Open API Documentation")
                                .version("1.0")
                                .description("Open API Documentation")
                                .license(
                                        new License()
                                                .name("API license")
                                                .url("https://www.google.com")))
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "bearerAuth",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")))
                .security(List.of(new SecurityRequirement().addList("bearerAuth")));
    }

    @Bean
    public GroupedOpenApi authAPI() {
        return GroupedOpenApi.builder()
                .group("auth api")
                .packagesToScan("com.gearx.feature.security")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public GroupedOpenApi productAPI() {
        return GroupedOpenApi.builder()
                .group("product api")
                .packagesToScan("com.gearx.feature.product")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public GroupedOpenApi branchAPI() {
        return GroupedOpenApi.builder()
                .group("branch api")
                .packagesToScan("com.gearx.feature.brand")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public GroupedOpenApi categoryAPI() {
        return GroupedOpenApi.builder()
                .group("category api")
                .packagesToScan("com.gearx.feature.category")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public GroupedOpenApi cartAPI() {
        return GroupedOpenApi.builder()
                .group("cart api")
                .packagesToScan("com.gearx.feature.cart")
                .pathsToMatch("/api/**")
                .build();
    }
}
