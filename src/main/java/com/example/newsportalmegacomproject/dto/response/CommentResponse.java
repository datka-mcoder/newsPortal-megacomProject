package com.example.newsportalmegacomproject.dto.response;

import com.example.newsportalmegacomproject.db.model.Comment;
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
public class CommentResponse {

    private Long id;
    private String text;
    private LocalDate commentedDate;
    private CommentedUserResponse commentedUserResponse;
    private List<ReplyCommentResponse> replyCommentResponses;

    public CommentResponse(Long id, String text, LocalDate commentedDate) {
        this.id = id;
        this.text = text;
        this.commentedDate = commentedDate;
    }
}
