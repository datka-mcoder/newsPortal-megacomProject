package com.example.newsportalmegacomproject.db.service;

import com.example.newsportalmegacomproject.db.model.Comment;
import com.example.newsportalmegacomproject.db.model.News;
import com.example.newsportalmegacomproject.db.model.ReplyComment;
import com.example.newsportalmegacomproject.db.model.User;
import com.example.newsportalmegacomproject.db.repository.CommentRepository;
import com.example.newsportalmegacomproject.db.repository.NewsRepository;
import com.example.newsportalmegacomproject.db.repository.UserRepository;
import com.example.newsportalmegacomproject.dto.request.CommentRequest;
import com.example.newsportalmegacomproject.dto.request.ReplyToCommentRequest;
import com.example.newsportalmegacomproject.dto.response.CommentResponse;
import com.example.newsportalmegacomproject.dto.response.CommentedUserResponse;
import com.example.newsportalmegacomproject.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByNickName(login).orElseThrow(
                () -> new NotFoundException("User with nick name: " + login + " not found!")
        );
    }


    public CommentResponse addComment(CommentRequest request) {
        User user = getAuthenticateUser();
        News news = newsRepository.findById(request.getNewsId()).orElseThrow(
                () -> new NotFoundException("News with id: " + request.getNewsId() + " not found!")
        );

        Comment comment = new Comment(request);
        comment.setNews(news);
        comment.setUser(user);
        Comment save = commentRepository.save(comment);
        CommentedUserResponse userResponse = userRepository.getCommentedUser(save.getUser().getId());
        return new CommentResponse(
                save.getId(),
                save.getText(),
                save.getCommentedDate(),
                userResponse,
                new ArrayList<>()
        );
    }

    public CommentResponse replyToComment(Long commentId, ReplyToCommentRequest request) {
        User user = getAuthenticateUser();
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("Comment with id: " + commentId + " not found!")
        );

        News news = newsRepository.findById(comment.getNews().getId()).orElseThrow(
                () -> new NotFoundException("News with id: " + comment.getNews().getId() + " not found!")
        );

        ReplyComment replyComment = new ReplyComment(comment.getId(), request.getText());
        replyComment.setComment(comment);
        comment.addReplyComment(replyComment);

        return new CommentResponse(
                save.getId(),
                save.getText(),
                save.getCommentedDate(),
                userResponse
        );
    }
}
