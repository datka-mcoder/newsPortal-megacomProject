package com.example.newsportalmegacomproject.db.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "reply-comments")
@Getter
@Setter
@NoArgsConstructor
public class ReplyComment {

    @Id
    @GeneratedValue(generator = "reply-gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "reply-gen", sequenceName = "reply-seq", allocationSize = 1)
    private Long id;

    private String text;

    @ManyToOne(cascade = {DETACH, REFRESH, MERGE})
    private Comment comment;

    @ManyToOne(cascade = {DETACH, REFRESH, MERGE})
    private User user;

    public ReplyComment(Long id, String text) {
        this.id = id;
        this.text = text;
    }
}
