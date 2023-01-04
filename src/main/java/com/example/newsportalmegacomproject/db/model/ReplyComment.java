package com.example.newsportalmegacomproject.db.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "reply_comments")
@Getter
@Setter
@NoArgsConstructor
public class ReplyComment {

    @Id
    @GeneratedValue(generator = "reply_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "reply_gen", sequenceName = "reply_seq", allocationSize = 1)
    private Long id;

    private String text;

    private LocalDate createdAt;

    @ManyToOne(cascade = {DETACH, REFRESH, MERGE})
    private Comment comment;

    @ManyToOne(cascade = {DETACH, REFRESH, MERGE})
    private User user;

    public ReplyComment(Long id, String text) {
        this.id = id;
        this.text = text;
        this.createdAt = LocalDate.now();
    }
}
