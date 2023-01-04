package com.example.newsportalmegacomproject.db.model;

import com.example.newsportalmegacomproject.dto.request.CommentRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;


@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(generator = "comment_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "comment_gen", sequenceName = "comment_seq", allocationSize = 1)
    private Long id;

    private String text;

    private LocalDate createdAt;

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
