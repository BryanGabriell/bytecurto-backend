package com.bryangabriel.bytecurto.business.dto.in;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record LinkRequestDTO(
        @Schema(
                description = "A URL original longa que será encurtada",
                example = "https://google.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "O campo de url tem que estar preenchido")
        @URL(message = "Url invalida")
        String urlOriginal
) {
}
