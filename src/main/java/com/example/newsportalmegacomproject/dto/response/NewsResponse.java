package com.example.newsportalmegacomproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponse {

    private Long id;
    private String title;
    private String description;
    private String text;
    private LocalDate createdAt;
    private String imageCover;
    private FavoriteResponse favoriteResponse;
    private List<CommentResponse> commentResponses;
}
