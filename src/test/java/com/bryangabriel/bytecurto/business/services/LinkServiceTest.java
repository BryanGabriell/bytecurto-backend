package com.bryangabriel.bytecurto.business.services;

import com.bryangabriel.bytecurto.business.dto.in.LinkRequestDTO;
import com.bryangabriel.bytecurto.business.dto.out.LinkResponseDTO;
import com.bryangabriel.bytecurto.business.mapstruct.LinkMapper;
import com.bryangabriel.bytecurto.business.services.component.ShortCodeGenerator;
import com.bryangabriel.bytecurto.infrastructure.entity.Link;
import com.bryangabriel.bytecurto.infrastructure.entity.User;
import com.bryangabriel.bytecurto.infrastructure.entity.repositorys.LinkRepository;
import com.bryangabriel.bytecurto.infrastructure.exceptions.UrlNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class LinkServiceTest {
    @Mock
    private  LinkRepository linkRepository;
    @Mock
    private ShortCodeGenerator shortCodeGenerator;
    @Mock
    private  LinkMapper linkMapper;

    @InjectMocks
    LinkService linkService;

    @Nested
    @DisplayName("Deve retornar sucesso")
    class shouldReturnSuccess {
        @Test
        @DisplayName("Deve retornar sucesso ao encurtar a url")
        void shouldReturnToTheShortenUrlSuccess(){
            String urlOriginal = "https://www.linkedin.com/in/bryan-gabriell/";
            LinkRequestDTO linkRequestDTO = new LinkRequestDTO(urlOriginal);
            LinkResponseDTO linkResponseDTO = new LinkResponseDTO("https://bytecurto.com/",urlOriginal);

            when(shortCodeGenerator.geradorDeCodigo(linkRequestDTO)).thenReturn(linkResponseDTO);

            var out = linkService.encurtarUrl(linkRequestDTO);

            assertNotNull(out);

            verify(shortCodeGenerator).geradorDeCodigo(any());
        }
        @Test
        @DisplayName("Deve Retornar sucesso ao obter a url")
        void shouldReturnToTheGetOriginalUrl(){
            String urlOriginal = "https://linkedin.com";
            String shortCode = "abc123xy";
            LocalDateTime createdAt = LocalDateTime.of(2026, 7, 10, 17, 49);
            LocalDateTime updateAt = LocalDateTime.of(2026, 7, 10, 17, 49);
           Long id = 1L;

            User user = new User();
            user.setId(id);
            user.setName("Bryan");
            user.setEmail("detergente@gmail.com");
            user.setPassword("detergente123");
            user.setCreatedAt(createdAt);
            user.setUpdateAt(updateAt);

           Link link = new Link();
           link.setId(id);
           link.setUrlOriginal(urlOriginal);
           link.setUser(user);
           link.setShortCode(shortCode);
           link.setCreatedAt(createdAt);
           link.setUpdateAt(updateAt);

           LinkResponseDTO linkResponseDTO = new LinkResponseDTO(shortCode,urlOriginal);

            when(linkRepository.findByShortCode(shortCode)).thenReturn(Optional.of(link));
           when(linkMapper.paraOut(link)).thenReturn(linkResponseDTO);

           var output = linkService.obterUrlOriginal(shortCode);

           assertNotNull(output);

           verify(linkMapper).paraOut(link);
        }
    }
    @Nested
    @DisplayName("Testes de falha")
    class shouldReturnErrors{

        @DisplayName("Deve Retornar erro caso url não exista")
        @Test
        void shouldReturnIfUrlNotFound(){
            String shortCode = "abc123xy";

            when(linkRepository.findByShortCode(shortCode)).thenReturn(Optional.empty());
            assertThatThrownBy(()
                            -> linkService.obterUrlOriginal(shortCode)).
                    isInstanceOf(UrlNotFound.class).
                    hasMessage("Url não existe");

            verifyNoInteractions(linkMapper);
        }
    }
}