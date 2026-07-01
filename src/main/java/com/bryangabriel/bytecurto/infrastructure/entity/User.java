package com.bryangabriel.bytecurto.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name",nullable = false, length = 60)
    private  String name;
    @Column(name = "email", nullable = false, length = 254, unique = true)
    private String email;
    @Column(name = "password", nullable = false,length = 255)
    private String password;

    @OneToMany(mappedBy = "user", cascade =CascadeType.ALL, orphanRemoval = true)
    private List<Link> links;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;
}
