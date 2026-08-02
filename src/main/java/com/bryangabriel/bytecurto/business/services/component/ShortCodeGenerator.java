package com.bryangabriel.bytecurto.business.services.component;


import com.bryangabriel.bytecurto.business.dto.in.LinkRequestDTO;
import com.bryangabriel.bytecurto.business.dto.out.LinkResponseDTO;
import com.bryangabriel.bytecurto.business.mapstruct.LinkMapper;
import com.bryangabriel.bytecurto.infrastructure.entity.Link;
import com.bryangabriel.bytecurto.infrastructure.entity.User;
import com.bryangabriel.bytecurto.infrastructure.entity.repositorys.LinkRepository;
import com.bryangabriel.bytecurto.infrastructure.entity.repositorys.UserRepository;
import com.bryangabriel.bytecurto.infrastructure.exceptions.UserNotFound;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ShortCodeGenerator {

    private final LinkRepository linkRepository;
     private final LinkMapper linkMapper;
     private final UserRepository userRepository;

    @Value("${app.base-url:https://bytecurto-frontend.vercel.app}")
    private String baseUrl;

    public ShortCodeGenerator(LinkRepository linkRepository, LinkMapper linkMapper, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.linkMapper = linkMapper;
        this.userRepository = userRepository;
    }


    public LinkResponseDTO geradorDeCodigo(LinkRequestDTO linkRequestDTO){
        String shortCode = gerarShortCodeUnico();

        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameOuEmail = jwt.getSubject();

        User user = userRepository.findByEmail(usernameOuEmail)
                .orElseThrow(() -> new UserNotFound("Usuário autenticado não encontrado na base de dados"));

        String urlOriginalSanitizada = sanitizarUrl(linkRequestDTO.urlOriginal());
        LinkRequestDTO dtoSanitizado = new LinkRequestDTO(urlOriginalSanitizada);

        Link linkEntity = linkMapper.paraEntity(dtoSanitizado, shortCode, user);
        Link linkSalvo = linkRepository.save(linkEntity);

        String baseLimpa = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;

        String urlEncurtadaCompleta = baseUrl + "/redirecionar/" + linkSalvo.getShortCode();

        return new LinkResponseDTO(urlEncurtadaCompleta, linkSalvo.getUrlOriginal());
    }
    private String sanitizarUrl(String url) {
        if (url != null && !url.startsWith("http://") && !url.startsWith("https://")) {
            return "https://" + url;
        }
        return url;
    }

    private String gerarShortCodeUnico() {
        log.info("Gerando código encurtado aleatório");
        String code;
        do {
            code = RandomStringUtils.randomAlphanumeric(10);
        } while (linkRepository.existsByShortCode(code));
        return code;
    }
}
