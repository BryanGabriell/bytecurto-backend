package com.bryangabriel.bytecurto.business.dto.out;


import io.swagger.v3.oas.annotations.media.Schema;

public record LinkResponseDTO(
        @Schema(
        description = "O link encurtado final pronto para ser compartilhado",
        example = "https://bytecurto.com"
) String shortCode,
        String urlOriginal
) {
}
