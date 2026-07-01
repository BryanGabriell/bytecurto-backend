package com.bryangabriel.bytecurto.business.mapstruct;


import com.bryangabriel.bytecurto.business.dto.in.UserRequestDTO;
import com.bryangabriel.bytecurto.business.dto.out.UserResponseDTO;
import com.bryangabriel.bytecurto.infrastructure.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User paraEntity(UserRequestDTO userRequestDTO);

    UserResponseDTO paraOut(User user);
}
