package com.example.newsportalmegacomproject.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentRequest {

    private Long newsId;
    private String text;
}
