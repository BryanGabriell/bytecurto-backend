package com.bryangabriel.bytecurto.business.services;

import com.bryangabriel.bytecurto.business.dto.in.LinkRequestDTO;
import com.bryangabriel.bytecurto.business.dto.out.LinkResponseDTO;
import com.bryangabriel.bytecurto.business.mapstruct.LinkMapper;
import com.bryangabriel.bytecurto.business.services.component.ShortCodeGenerator;
import com.bryangabriel.bytecurto.infrastructure.entity.repositorys.LinkRepository;
import com.bryangabriel.bytecurto.infrastructure.exceptions.UrlNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
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
        log.info("Requisição recebida para encurtar a Url [{}]", linkRequestDTO.urlOriginal());
    return shortCodeGenerator.geradorDeCodigo(linkRequestDTO);
    }

    public LinkResponseDTO obterUrlOriginal(String shortCode){
        log.info("Buscando a url original para o shortCode {}", shortCode);
       var url = linkRepository.findByShortCode(shortCode).orElseThrow(() ->{
                   log.warn("Url encurtada não encontrada no banco");
                   return new UrlNotFound("Url não existe");
               });


       log.debug("Mapeando url encontrada {}",url.getUrlOriginal());
        return linkMapper.paraOut(url);
    }
}
