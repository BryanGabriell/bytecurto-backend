package com.bryangabriel.bytecurto.business.services;

import com.bryangabriel.bytecurto.business.dto.in.LinkRequestDTO;
import com.bryangabriel.bytecurto.business.dto.out.LinkResponseDTO;
import com.bryangabriel.bytecurto.business.mapstruct.LinkMapper;
import com.bryangabriel.bytecurto.business.services.component.ShortCodeGenerator;
import com.bryangabriel.bytecurto.infrastructure.entity.repositorys.LinkRepository;
import com.bryangabriel.bytecurto.infrastructure.exceptions.UrlNotFound;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
   private final ShortCodeGenerator shortCodeGenerator;
    private final LinkMapper linkMapper;

    public LinkService(LinkRepository linkRepository, ShortCodeGenerator shortCodeGenerator, LinkMapper linkMapper) {
        this.linkRepository = linkRepository;
        this.shortCodeGenerator = shortCodeGenerator;
        this.linkMapper = linkMapper;
    }
    @Transactional
    public LinkResponseDTO encurtarUrl(LinkRequestDTO linkRequestDTO){
    return shortCodeGenerator.geradorDeCodigo(linkRequestDTO);
    }

    public LinkResponseDTO obterUrlOriginal(String shortCode){
       var url = linkRepository.findByShortCode(shortCode).orElseThrow(() ->
                new UrlNotFound("Url não existe"));

        return linkMapper.paraOut(url);
    }
}
