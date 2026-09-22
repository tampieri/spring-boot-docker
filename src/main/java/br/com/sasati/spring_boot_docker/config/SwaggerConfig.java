package br.com.sasati.spring_boot_docker.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi apiGroup() {
        return GroupedOpenApi.builder()
                .group("api-principal")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public GroupedOpenApi dataRestGroup() {
        return GroupedOpenApi.builder()
                .group("spring-data-rest")
                .pathsToMatch("/data-rest/**")
                .build();
    }
}
