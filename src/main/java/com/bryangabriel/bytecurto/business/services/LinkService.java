package com.bryangabriel.bytecurto.business.services;

import com.bryangabriel.bytecurto.business.dto.in.LinkRequestDTO;
import com.bryangabriel.bytecurto.business.dto.out.LinkResponseDTO;
import com.bryangabriel.bytecurto.infrastructure.entity.repositorys.LinkRepository;
import org.springframework.stereotype.Service;

@Service
public class LinkService {

   private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public LinkResponseDTO encurtarUrl(LinkRequestDTO linkRequestDTO){

    }
}
