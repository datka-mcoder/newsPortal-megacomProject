package com.example.newsportalmegacomproject.db.model;

import com.example.newsportalmegacomproject.dto.request.CommentRequest;
import com.example.newsportalmegacomproject.dto.request.ReplyToCommentRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(generator = "comment-gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "comment-gen", sequenceName = "comment-seq", allocationSize = 1)
    private Long id;

    private String text;

    private LocalDate createdAt;

    @OneToMany(cascade = {ALL})
    private List<Comment> comments;

    @ManyToOne
    private User user;

    @ManyToOne(cascade = {DETACH, REFRESH, MERGE})
    private News news;

    @OneToMany(cascade = {ALL}, mappedBy = "comment")
    private List<ReplyComment> replyComments;

    public Comment(CommentRequest request) {
        this.text = request.getText();
        this.createdAt = LocalDate.now();
    }

    public void addReplyComment(ReplyComment replyComment) {
        if (replyComments == null) {
            replyComments = new ArrayList<>();
        }
        replyComments.add(replyComment);
    }
}
