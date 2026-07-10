package com.bryangabriel.bytecurto.business.services.component;


import com.bryangabriel.bytecurto.business.dto.in.LinkRequestDTO;
import com.bryangabriel.bytecurto.business.dto.out.LinkResponseDTO;
import com.bryangabriel.bytecurto.business.mapstruct.LinkMapper;
import com.bryangabriel.bytecurto.infrastructure.entity.Link;
import com.bryangabriel.bytecurto.infrastructure.entity.User;
import com.bryangabriel.bytecurto.infrastructure.entity.repositorys.LinkRepository;
import com.bryangabriel.bytecurto.infrastructure.entity.repositorys.UserRepository;
import com.bryangabriel.bytecurto.infrastructure.exceptions.UserNotFound;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class ShortCodeGenerator {

    private final LinkRepository linkRepository;
     private final LinkMapper linkMapper;
     private final UserRepository userRepository;

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


        Link linkEntity = linkMapper.paraEntity(linkRequestDTO, shortCode, user);
        Link linkSalvo = linkRepository.save(linkEntity);

        return new LinkResponseDTO("https://bytecurto.com/" + linkSalvo.getShortCode(), linkSalvo.getUrlOriginal());
    }

    private String gerarShortCodeUnico() {
        String code;
        do {
            code = RandomStringUtils.randomAlphanumeric(10);
        } while (linkRepository.existsByShortCode(code));
        return code;
    }
}
