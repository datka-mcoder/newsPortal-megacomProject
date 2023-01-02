package com.example.newsportalmegacomproject.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyToCommentRequest {

    private Long commentId;
    private String text;
}
