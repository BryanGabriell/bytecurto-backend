package com.bryangabriel.bytecurto.repositorys;


import com.bryangabriel.bytecurto.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
}
