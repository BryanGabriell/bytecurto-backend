package com.bryangabriel.bytecurto.business.controller;


import com.bryangabriel.bytecurto.business.dto.in.UserRequestDTO;
import com.bryangabriel.bytecurto.business.dto.out.UserResponseDTO;
import com.bryangabriel.bytecurto.business.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("v1/users")
public class  UserController {
 private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
 public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO, UriComponentsBuilder uriComponent){
  var user = userService.createUser(userRequestDTO);

     URI uri = uriComponent.path("/v1/users/{id}")
                     .buildAndExpand(user.id())
                             .toUri();
   return ResponseEntity.created(uri).body(user);
 }
}
