package com.example.newsportalmegacomproject.db.repository;

import com.example.newsportalmegacomproject.db.model.ReplyComment;
import com.example.newsportalmegacomproject.dto.request.ReplyToCommentRequest;
import com.example.newsportalmegacomproject.dto.response.ReplyCommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyCommentRepository extends JpaRepository<ReplyComment, Long> {

    @Query("select new com.example.newsportalmegacomproject.dto.response.ReplyCommentResponse(" +
            "r.id, " +
            "r.text, " +
            "r.createdAt) from ReplyComment r where r.comment.id = :id")
    List<ReplyCommentResponse> getAllReplyCommentResponse(Long id);
}