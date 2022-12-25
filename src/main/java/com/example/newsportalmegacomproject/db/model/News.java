package com.example.newsportalmegacomproject.db.model;

import com.example.newsportalmegacomproject.enums.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "news")
@Getter
@Setter
@NoArgsConstructor
public class News {

    @Id
    @GeneratedValue(generator = "news_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "news_gen", sequenceName = "news_seq", allocationSize = 1)
    private Long id;

    private String title;

    private String text;

    private String imageCover;

    private LocalDate createdAt;

    @ManyToOne(cascade = {ALL})
    private User user;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToOne(cascade = {ALL}, mappedBy = "news")
    private Favorite favorite;
}
