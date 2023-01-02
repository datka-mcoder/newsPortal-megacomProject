package com.example.newsportalmegacomproject.db.repository;

import com.example.newsportalmegacomproject.db.model.Comment;
import com.example.newsportalmegacomproject.dto.response.CommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select new com.example.newsportalmegacomproject.dto.response.CommentResponse(" +
            "c.id, " +
            "c.text, " +
            "c.createdAt) from Comment c where c.news.id = :id order by c.id desc ")
    List<CommentResponse> getAllCommentResponsesByNewsId(Long id);

    @Query("select c from Comment c where c.news.id = :newsId order by c.id desc ")
    List<Comment> getAllNewsComment(Long newsId);
}