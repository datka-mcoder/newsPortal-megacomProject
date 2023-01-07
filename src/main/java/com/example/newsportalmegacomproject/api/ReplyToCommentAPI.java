package com.example.newsportalmegacomproject.api;

import com.example.newsportalmegacomproject.db.service.ReplyToCommentService;
import com.example.newsportalmegacomproject.dto.request.CommentRequest;
import com.example.newsportalmegacomproject.dto.response.CommentResponse;
import com.example.newsportalmegacomproject.dto.response.ReplyCommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reply-comments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Reply comment API", description = "All endpoints of reply comments")
public class ReplyToCommentAPI {

    private final ReplyToCommentService replyToCommentService;

    @Operation(summary = "Reply comment", description = "Reply to comment by comment id")
    @PostMapping
    public CommentResponse replyComment(@RequestBody CommentRequest request) {
        return replyToCommentService.replyComment(request);
    }

    @Operation(summary = "Get all reply comments", description = "Get all reply comments by comment id")
    @GetMapping("/{id}")
    public List<ReplyCommentResponse> getAllReplyComments(@PathVariable Long id) {
        return replyToCommentService.getAllReplyComments(id);
    }
}
