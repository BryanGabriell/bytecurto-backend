package com.bryangabriel.bytecurto.business.controller;

import com.bryangabriel.bytecurto.business.dto.in.LoginRequestDTO;
import com.bryangabriel.bytecurto.business.dto.out.LoginResponseDTO;
import com.bryangabriel.bytecurto.infrastructure.config.SecurityConfig;
import com.bryangabriel.bytecurto.infrastructure.security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = SecurityConfig.SECURITY)
@Tag(name = "AuthController", description = "Controlador de login de usuários")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Realiza o login do usuário", description = "Controlador onde valida o login de usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso. Retorna o Token JWT"),
            @ApiResponse(responseCode = "400", description = "Descrição malformada ou campos obrigatórios"),
            @ApiResponse(responseCode = "401", description = "Email ou senha inválidos"),
            @ApiResponse(responseCode = "429", description = "Muitas tentativas de login, Conta bloqueada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    }
    )
     public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        var dadosAutenticacao = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(),loginRequestDTO.password());
        Authentication authentication = authenticationManager.authenticate(dadosAutenticacao);

        String jwtToken = authService.gerarToken(authentication);

        return ResponseEntity.ok(new LoginResponseDTO(jwtToken,3600L));
    }
}
