package com.bryangabriel.bytecurto.business.controller;

import com.bryangabriel.bytecurto.business.dto.in.LoginRequestDTO;
import com.bryangabriel.bytecurto.business.dto.out.LoginResponseDTO;
import com.bryangabriel.bytecurto.infrastructure.security.AuthService;
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
 public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
var dadosAutenticacao = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(),loginRequestDTO.password());
Authentication authentication = authenticationManager.authenticate(dadosAutenticacao);

String jwtToken = authService.gerarToken(authentication);

return ResponseEntity.ok(new LoginResponseDTO(jwtToken,3600L));
    }
}
