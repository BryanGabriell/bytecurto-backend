package com.bryangabriel.bytecurto.business.dto.out;


import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String name,
    String email,
        LocalDateTime createdAt,
        LocalDateTime updateAt
) {
}
