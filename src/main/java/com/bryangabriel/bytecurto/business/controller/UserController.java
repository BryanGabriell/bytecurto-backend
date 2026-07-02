package com.bryangabriel.bytecurto.business.controller;


import com.bryangabriel.bytecurto.business.dto.in.UserRequestDTO;
import com.bryangabriel.bytecurto.business.dto.out.UserResponseDTO;
import com.bryangabriel.bytecurto.business.services.UserService;
import com.bryangabriel.bytecurto.infrastructure.config.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("v1/users")
@Tag(name = "user", description = "Controlador para criar um usuário")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class  UserController {
 private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Cria um novo usuário", description = "Método para criar um usuario")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Email ja cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro no servidor")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO, UriComponentsBuilder uriComponent){
    var user = userService.createUser(userRequestDTO);

     URI uri = uriComponent.path("/v1/users/{id}")
                     .buildAndExpand(user.id())
                             .toUri();
         return ResponseEntity.created(uri).body(user);
 }
}
