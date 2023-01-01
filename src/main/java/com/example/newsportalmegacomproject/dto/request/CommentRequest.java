package com.example.newsportalmegacomproject.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {

    private Long newsId;
    private String text;
}
