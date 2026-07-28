package com.bryangabriel.bytecurto.business.services;


import com.bryangabriel.bytecurto.business.dto.in.UserRequestDTO;
import com.bryangabriel.bytecurto.business.dto.out.UserResponseDTO;
import com.bryangabriel.bytecurto.business.mapstruct.UserMapper;
import com.bryangabriel.bytecurto.infrastructure.entity.User;
import com.bryangabriel.bytecurto.infrastructure.entity.repositorys.UserRepository;
import com.bryangabriel.bytecurto.infrastructure.exceptions.EmailAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO dto){
      log.info("Criando novo usuário no sistema {}", dto.name());
        if (userRepository.existsByEmail(dto.email())){
            log.warn("E-mail ja existe no banco de dados {}", dto.email());
            throw new EmailAlreadyExistsException("O email digitado ja existe");
        }

        User user = userMapper.paraEntity(dto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        log.debug("Mapeando Usuário criado {}", dto.name());

        userRepository.save(user);
       return userMapper.paraOut(user);
    }
}
