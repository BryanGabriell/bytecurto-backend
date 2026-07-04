package com.bryangabriel.bytecurto.business.mapstruct;


import com.bryangabriel.bytecurto.business.dto.in.LinkRequestDTO;
import com.bryangabriel.bytecurto.business.dto.out.LinkResponseDTO;
import com.bryangabriel.bytecurto.infrastructure.entity.Link;
import com.bryangabriel.bytecurto.infrastructure.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LinkMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "urlOriginal", source = "linkRequestDTO.urlOriginal")
    @Mapping(target = "shortCode", source = "shortCode")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updateAt", ignore = true)
    Link paraEntity(LinkRequestDTO linkRequestDTO, String shortCode, User user);

    LinkResponseDTO paraOut(Link link);
}
