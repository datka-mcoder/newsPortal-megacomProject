package com.example.newsportalmegacomproject.dto.response;

import com.example.newsportalmegacomproject.db.model.ReplyComment;
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

    public ReplyCommentResponse(ReplyComment replyComment) {
        this.id = replyComment.getId();
        this.text = replyComment.getText();
        this.createdAt = replyComment.getCreatedAt();
    }
}
