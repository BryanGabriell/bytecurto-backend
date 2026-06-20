package com.bryangabriel.bytecurto.repositorys;


import com.bryangabriel.bytecurto.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
