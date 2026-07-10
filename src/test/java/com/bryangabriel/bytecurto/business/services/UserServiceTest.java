package com.bryangabriel.bytecurto.business.services;

import com.bryangabriel.bytecurto.business.dto.in.UserRequestDTO;
import com.bryangabriel.bytecurto.business.dto.out.UserResponseDTO;
import com.bryangabriel.bytecurto.business.mapstruct.UserMapper;
import com.bryangabriel.bytecurto.infrastructure.entity.User;
import com.bryangabriel.bytecurto.infrastructure.entity.repositorys.UserRepository;
import com.bryangabriel.bytecurto.infrastructure.exceptions.EmailAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Nested
    @DisplayName("Testes De sucesso")
    class shouldReturnSuccess{
        @Test
        @DisplayName("Deve Criar usuário com sucesso")
    void shouldReturnSuccessToTheCreateUser(){
    Long id = 1L;
    LocalDateTime createdAt = LocalDateTime.of(2026, 7, 10, 17, 49);
    LocalDateTime updateAt = LocalDateTime.of(2026, 7, 10, 17, 49);
    User user = new User();
    user.setId(id);
    user.setName("Bryan");
    user.setEmail("detergente@gmail.com");
    user.setPassword("detergente123");
    user.setCreatedAt(createdAt);
    user.setUpdateAt(updateAt);


    UserRequestDTO userRequestDTO = new UserRequestDTO(user.getName(), user.getEmail(), user.getPassword());

    UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdateAt());


    when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
    when(userMapper.paraEntity(userRequestDTO)).thenReturn(user);
    when(passwordEncoder.encode(userRequestDTO.password())).thenReturn(user.getPassword());
    when(userRepository.save(any(User.class))).thenReturn(user);

    when(userMapper.paraOut(user)).thenReturn(userResponseDTO);

    var out = userService.createUser(userRequestDTO);

    assertNotNull(out);
    assertEquals(userResponseDTO.id(),out.id());
    assertEquals(userResponseDTO.name(), out.name());
    assertEquals(userResponseDTO.email(), out.email());

    verify(passwordEncoder).encode(any());
    verify(userRepository).save(user);
    verify(userRepository).existsByEmail(userRequestDTO.email());
    verify(userMapper).paraOut(user);
    verify(userMapper).paraEntity(userRequestDTO);
    }
    }

    @Nested
    @DisplayName("Testes de falha")
    class shouldReturnErrors{

        @Test
        @DisplayName("Deve Retornar erro se email do usuario ja existir")
        void shouldReturnIfEmailExists(){
            String emailExistente = "detergente123@gmail.com";

            UserRequestDTO userRequestDTO = new UserRequestDTO("Bryan", emailExistente, "detergente123");

            when(userRepository.existsByEmail(userRequestDTO.email())).thenReturn(true);

            assertThatThrownBy(() -> userService.createUser(userRequestDTO))
                    .isInstanceOf(EmailAlreadyExistsException.class)
                    .hasMessage("O email digitado ja existe")
                    .hasMessageContaining("ja existe");

            verify(userRepository, never()).save(any());
            verifyNoInteractions(userMapper);
            verifyNoInteractions(passwordEncoder);
        }
    }
}