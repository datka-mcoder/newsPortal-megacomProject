package com.example.newsportalmegacomproject.api;

import com.example.newsportalmegacomproject.db.service.CommentService;
import com.example.newsportalmegacomproject.dto.response.CommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/comments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Comment API", description = "All endpoints of Comment")
public class CommentAPI {

    private final CommentService commentService;

    @Operation(summary = "Add comment to news", description = "Add comment to news")
    @PostMapping
    public CommentResponse addCommentToNews(CommentRequest request) {
        return commentService.addComment(request);
    }
}
