package com.bryangabriel.bytecurto.infrastructure.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BYTE CURTO")
                        .version("1.0.0")
                        .description("Documentação da API do Byte Curto um encurtador de Urls")
                        .contact(new Contact()
                                .name("Bryan Gabriel")
                                .email("bryanfinanci@gmail.com")
                                .url("https://github.com/BryanGabriell"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));

    }
}
