package com.bryangabriel.bytecurto.business.controller;


import com.bryangabriel.bytecurto.business.services.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redirecionar")
@CrossOrigin(origins = "*")
public class RedirectController {
    private final LinkService linkService;

    public RedirectController(LinkService linkService) {
        this.linkService = linkService;
    }

    @Operation(summary = "Redireciona para a URL original",
            description = "Recebe o código curto de 10 caracteres e redireciona o visitante para o site original correspondente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redirecionamento bem-sucedido. O navegador será levado para a URL original."),
            @ApiResponse(responseCode = "404", description = "Link não encontrado. O código informado não existe no sistema.")
    })
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirecionar(@PathVariable String shortCode) {
        var responseDTO = linkService.obterUrlOriginal(shortCode);

        String urlOriginalLonga = responseDTO.urlOriginal();

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, urlOriginalLonga)
                .build();
    }
}
