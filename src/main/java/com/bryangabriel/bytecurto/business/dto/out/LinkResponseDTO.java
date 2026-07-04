package com.bryangabriel.bytecurto.business.dto.out;


import io.swagger.v3.oas.annotations.media.Schema;

public record LinkResponseDTO(
        @Schema(
                description = "O código identificador único da URL encurtada",
                example = "a8X9z"
        )
        String shortCode
) {
}
