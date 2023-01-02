package com.example.newsportalmegacomproject.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReplyCommentResponse {

    private Long id;
    private String text;
    private LocalDate replyCommentedDate;
    private CommentedUserResponse userResponse;

    public ReplyCommentResponse(Long id, String text, LocalDate replyCommentedDate) {
        this.id = id;
        this.text = text;
        this.replyCommentedDate = replyCommentedDate;
    }
}
