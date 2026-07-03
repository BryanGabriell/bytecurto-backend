package com.bryangabriel.bytecurto.business.dto.out;



public record LoginResponseDTO(
        String token,
        long tempoExpiracao
) {
}
