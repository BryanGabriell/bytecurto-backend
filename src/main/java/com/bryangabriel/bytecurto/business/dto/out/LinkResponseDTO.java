package com.bryangabriel.bytecurto.business.dto.out;


import io.swagger.v3.oas.annotations.media.Schema;

public record LinkResponseDTO(
        @Schema(
        description = "O link encurtado final pronto para ser compartilhado",
        example = "https://bytecurto-frontend.vercel.app/redirecionar/q1u3y3P1VP"
) String shortCode,
        String urlOriginal
) {
}
