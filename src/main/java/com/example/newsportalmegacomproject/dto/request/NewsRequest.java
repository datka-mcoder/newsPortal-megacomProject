package com.example.newsportalmegacomproject.dto.request;

import com.example.newsportalmegacomproject.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsRequest {

    private String imageCover;
    private String title;
    private String description;
    private String text;

    @Enumerated(EnumType.STRING)
    private Category category;
}
