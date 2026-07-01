package com.bryangabriel.bytecurto.infrastructure.entity.repositorys;


import com.bryangabriel.bytecurto.infrastructure.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
}
