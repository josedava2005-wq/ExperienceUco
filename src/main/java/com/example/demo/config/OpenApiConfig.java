package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI experienceUcoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ExperienceUco API")
                        .description("API REST para la plataforma de gestión de eventos universitarios UCO. " +
                                     "Permite registrar usuarios, crear eventos e inscribir participantes con control de cupos.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("UCO Backend Team")
                                .email("soporte@uco.edu.co")));
    }
}
