package com.bryangabriel.bytecurto.business.services.component;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.bryangabriel.bytecurto.business.dto.in.LinkRequestDTO;
import com.bryangabriel.bytecurto.business.dto.out.LinkResponseDTO;
import com.bryangabriel.bytecurto.business.mapstruct.LinkMapper;
import com.bryangabriel.bytecurto.infrastructure.entity.Link;
import com.bryangabriel.bytecurto.infrastructure.entity.User;
import com.bryangabriel.bytecurto.infrastructure.entity.repositorys.LinkRepository;
import com.bryangabriel.bytecurto.infrastructure.entity.repositorys.UserRepository;
import com.bryangabriel.bytecurto.infrastructure.exceptions.UserNotFound;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;


@ExtendWith(MockitoExtension.class)
class ShortCodeGeneratorTest {
    @Mock
    private LinkRepository linkRepository;
    @Mock
    private LinkMapper linkMapper;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ShortCodeGenerator shortCodeGenerator;

    private MockedStatic<SecurityContextHolder> mockedSecurityContextHolder;
    private LinkRequestDTO requestDTO;
    private Jwt mockJwt;

    @BeforeEach
    void setUp() {

        requestDTO = new LinkRequestDTO("https://google.com");


        mockJwt = mock(Jwt.class);
        when(mockJwt.getSubject()).thenReturn("usuario@email.com");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(mockJwt);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);


        mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class);
        mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
    }

    @AfterEach
    void tearDown() {
        if (mockedSecurityContextHolder != null) {
            mockedSecurityContextHolder.close();
        }
    }

    @Nested
    @DisplayName("Testes de sucesso")
    class ShouldReturnSucess {

        @Test
        @DisplayName("Deve gerar o código encurtado e retornar o DTO com sucesso")
        void shouldReturnSucessToTheGenerateShortCode() {
            User mockUser = new User();
            Link linkEntity = new Link();
            linkEntity.setShortCode("ABC123XYZ4");
            linkEntity.setUrlOriginal(requestDTO.urlOriginal());

            when(userRepository.findByEmail("usuario@email.com")).thenReturn(Optional.of(mockUser));
            when(linkRepository.existsByShortCode(anyString())).thenReturn(false);
            when(linkMapper.paraEntity(eq(requestDTO), anyString(), eq(mockUser))).thenReturn(linkEntity);
            when(linkRepository.save(linkEntity)).thenReturn(linkEntity);


            LinkResponseDTO response = shortCodeGenerator.geradorDeCodigo(requestDTO);

            assertNotNull(response);
            assertEquals("https://google.com", response.urlOriginal());

            verify(linkRepository, times(1)).save(linkEntity);
        }
    }

    @Nested
    @DisplayName("Testes de erro")
    class ShouldReturnError {

        @Test
        @DisplayName("Deve lançar UserNotFound quando o usuário logado não existir no banco")
        void shouldThrowUserNotFoundWhenUserDoesNotExist() {
            when(userRepository.findByEmail("usuario@email.com")).thenReturn(Optional.empty());

            assertThrows(UserNotFound.class, () -> {
                shortCodeGenerator.geradorDeCodigo(requestDTO);
            });

            verify(linkMapper, never()).paraEntity(any(), any(), any());
            verify(linkRepository, never()).save(any());
        }
    }
}

