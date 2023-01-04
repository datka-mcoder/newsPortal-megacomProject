package com.example.newsportalmegacomproject.db.model;

import com.example.newsportalmegacomproject.dto.request.NewsRequest;
import com.example.newsportalmegacomproject.enums.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.CascadeType.*;

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

    private Boolean isFavorite = false;

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
        this.category = request.getCategory();
    }
}
