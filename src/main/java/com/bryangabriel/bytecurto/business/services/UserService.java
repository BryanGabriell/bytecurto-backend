package com.bryangabriel.bytecurto.business.services;


import com.bryangabriel.bytecurto.business.dto.in.UserRequestDTO;
import com.bryangabriel.bytecurto.business.dto.out.UserResponseDTO;
import com.bryangabriel.bytecurto.business.mapstruct.UserMapper;
import com.bryangabriel.bytecurto.infrastructure.entity.User;
import com.bryangabriel.bytecurto.infrastructure.entity.repositorys.UserRepository;
import com.bryangabriel.bytecurto.infrastructure.exceptions.EmailAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO createUser(UserRequestDTO dto){
        if (userRepository.existsByEmail(dto.email())){
            throw new EmailAlreadyExistsException("O email digitado ja existe");
        }

        User user = userMapper.paraEntity(dto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
       return userMapper.paraOut(user);
    }
}
