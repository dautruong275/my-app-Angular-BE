package com.dau.angular.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
             return new OpenAPI()
                .info(new Info()
                .title("API Dự Án Spring Boot")
                .version("1.0.0")
                .description("API mẫu với OpenAPI 3"))
                .addServersItem(new Server().url("http://localhost:9999").description("Local Server"));
    }
}
