package com.example.newsportalmegacomproject.dto.response;

import com.example.newsportalmegacomproject.db.model.News;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class MyNewsResponse {

    private Long id;
    private String title;
    private String description;
    private String imageCover;
    private LocalDate createdAt;

    public MyNewsResponse(News news) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.description = news.getDescription();
        this.imageCover = news.getImageCover();
        this.createdAt = news.getCreatedAt();
    }
}
