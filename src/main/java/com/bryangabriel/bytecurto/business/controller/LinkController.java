package com.bryangabriel.bytecurto.business.controller;


import com.bryangabriel.bytecurto.business.dto.in.LinkRequestDTO;
import com.bryangabriel.bytecurto.business.dto.out.LinkResponseDTO;
import com.bryangabriel.bytecurto.business.services.LinkService;
import com.bryangabriel.bytecurto.infrastructure.config.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/links")
@SecurityRequirement(name = SecurityConfig.SECURITY)
@CrossOrigin(origins = "*")
@Tag(name = "Links", description = "Controlador de encurtamento de links")
public class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }
    @PostMapping("/encurtar")
    @Operation(summary = "Encurta uma url",
            description = "Recebe uma Url Longa,gera um codigo de 10 caracteres unicos e associa usuario autenticado",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Código Gerado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos. URL ausente ou em formato incorreto."),
            @ApiResponse(responseCode = "401", description = "Não autorizado. Token JWT inválido ou expirado."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor ao tentar salvar o link.")
    })
    public ResponseEntity<LinkResponseDTO> encurtarUrl(@RequestBody @Valid LinkRequestDTO linkRequestDTO){
        var link = linkService.encurtarUrl(linkRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(link);
    }
}
