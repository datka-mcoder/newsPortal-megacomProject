package com.example.newsportalmegacomproject.dto.response;

import com.example.newsportalmegacomproject.db.model.News;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class NewsResponse {

    private Long id;
    private String title;
    private String description;
    private String imageCover;
    private LocalDate createdAt;
    private Boolean isFavorite;

    public NewsResponse(Long id, String title, String description, String imageCover, LocalDate createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageCover = imageCover;
        this.createdAt = createdAt;
    }

    public NewsResponse(News news) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.description = news.getDescription();
        this.imageCover = news.getImageCover();
        this.createdAt = news.getCreatedAt();
    }
}
