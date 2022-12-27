package com.example.newsportalmegacomproject.db.model;

import com.example.newsportalmegacomproject.dto.request.NewsRequest;
import com.example.newsportalmegacomproject.enums.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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

    private String description;

    private String text;

    private String imageCover;

    private LocalDate createdAt;

    private Boolean isFavorite;

    @ManyToOne(cascade = {ALL})
    private User user;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToOne(cascade = {ALL}, mappedBy = "news")
    private Favorite favorite;

    public News(NewsRequest request) {
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.text = request.getText();
        this.imageCover = request.getImageCover();
        this.createdAt = LocalDate.now();
    }
}
