package com.bryangabriel.bytecurto.business.dto.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 5, max = 60, message = "O nome deve ter entre 5 a 60 caracteres")
        String name,
        @Email(message = "O email tem ser valido")
        @NotBlank(message = "O campo email nao pode estar vazio")
        @Size(max = 254, message = "Maximo de caracteres 254")
        String email,
        @Size(min = 8, max = 255, message = "A senha deve ter entre 8 e 255 caracteres")
        @NotBlank(message = "A senha não pode estar vazia")
        String password
) {
}
