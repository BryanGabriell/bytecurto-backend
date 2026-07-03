package com.bryangabriel.bytecurto.business.dto.in;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @NotBlank(message = "O campo email não pode estar nulo")
        @Email(message = "Insira um email valido")
        String email,
      @NotBlank(message = "O campo senha não pode ser nulo")
      String senha
) {
}
