package com.example.newsportalmegacomproject.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReplyCommentResponse {

    private Long id;
    private String text;
    private LocalDate createdAt;
    private CommentedUserResponse userResponse;

    public ReplyCommentResponse(Long id, String text, LocalDate createdAt) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
    }
}
