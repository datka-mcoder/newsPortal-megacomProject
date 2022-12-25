package com.example.newsportalmegacomproject.db.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    private String firstName;

    private String lastName;

    private String nickName;

    private String image;

    @OneToOne(cascade = ALL)
    private AuthInfo authInfo;

    @OneToMany(cascade = ALL, mappedBy = "user")
    private List<News> news;

    @OneToMany(cascade = {ALL}, mappedBy = "user")
    private List<Favorite> favorites;
}
