package com.example.newsportalmegacomproject.db.service;

import com.example.newsportalmegacomproject.db.model.Comment;
import com.example.newsportalmegacomproject.db.model.News;
import com.example.newsportalmegacomproject.db.repository.CommentRepository;
import com.example.newsportalmegacomproject.db.repository.FavoriteRepository;
import com.example.newsportalmegacomproject.db.repository.NewsRepository;
import com.example.newsportalmegacomproject.dto.request.NewsRequest;
import com.example.newsportalmegacomproject.dto.response.CommentResponse;
import com.example.newsportalmegacomproject.dto.response.FavoriteResponse;
import com.example.newsportalmegacomproject.dto.response.NewsResponse;
import com.example.newsportalmegacomproject.dto.response.SimpleResponse;
import com.example.newsportalmegacomproject.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;
    private final FavoriteRepository favoriteRepository;


    public NewsResponse publishNews(NewsRequest request) {
        News news = new News(request);
        News save = newsRepository.save(news);
        return new NewsResponse(
                save.getId(),
                save.getTitle(),
                save.getDescription(),
                save.getText(),
                save.getCreatedAt(),
                save.getImageCover(),
                new FavoriteResponse(),
                new ArrayList<>()
        );
    }


    public NewsResponse getNewsById(Long id) {
        News news = newsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("News with id: " + id + " not found!")
        );

        List<Comment> comments = commentRepository.getAllNewsComment(news.getId());
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            commentResponses.add(commentRepository.getCommentResponse(comment.getId()));
        }

        FavoriteResponse favoriteResponse = favoriteRepository.getFavoriteResponse(news.getFavorite().getId());

        return new NewsResponse(
                news.getId(),
                news.getTitle(),
                news.getDescription(),
                news.getText(),
                news.getCreatedAt(),
                news.getImageCover(),
                favoriteResponse,
                commentResponses
        );
    }

    public SimpleResponse deleteNewsById(Long id) {
        News news = newsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("News with id: " + id + " not found!")
        );

        newsRepository.delete(news);

        return new SimpleResponse("News with id: " + id + " not found!", "DELETE");
    }
}
