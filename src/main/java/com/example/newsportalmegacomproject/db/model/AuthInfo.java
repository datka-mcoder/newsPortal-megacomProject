package com.example.newsportalmegacomproject.db.model;

import com.example.newsportalmegacomproject.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "auth-infos")
@Getter
@Setter
@NoArgsConstructor
public class AuthInfo {

    @Id
    @GeneratedValue(generator = "auth-info-gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "auth-info-gen", sequenceName = "auth-info-seq", allocationSize = 1)
    private Long id;

    private String email;

    private String password;

    private Role role;
}
