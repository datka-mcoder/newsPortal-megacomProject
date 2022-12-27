package com.example.newsportalmegacomproject.db.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    private LocalDate commentedDate;

    @ManyToOne
    private User user;

    @ManyToOne(cascade = {DETACH, REFRESH, MERGE})
    private News news;
}
