package com.example.newsportalmegacomproject.db.service;

import com.example.newsportalmegacomproject.db.model.Comment;
import com.example.newsportalmegacomproject.db.model.Favorite;
import com.example.newsportalmegacomproject.db.model.News;
import com.example.newsportalmegacomproject.db.model.User;
import com.example.newsportalmegacomproject.db.repository.CommentRepository;
import com.example.newsportalmegacomproject.db.repository.FavoriteRepository;
import com.example.newsportalmegacomproject.db.repository.NewsRepository;
import com.example.newsportalmegacomproject.db.repository.UserRepository;
import com.example.newsportalmegacomproject.dto.request.NewsRequest;
import com.example.newsportalmegacomproject.dto.response.CommentResponse;
import com.example.newsportalmegacomproject.dto.response.NewsResponse;
import com.example.newsportalmegacomproject.dto.response.SimpleResponse;
import com.example.newsportalmegacomproject.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserRepository userRepository;

    private User authenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByNickName(login).orElseThrow(
                () -> new NotFoundException("User not found!")
        );
    }


    public NewsResponse publishNews(NewsRequest request) {
        User user = authenticateUser();
        News news = new News(request);
        news.setUser(user);
        News save = newsRepository.save(news);
        return new NewsResponse(
                save.getId(),
                save.getTitle(),
                save.getDescription(),
                save.getText(),
                save.getCreatedAt(),
                save.getImageCover(),
                false,
                new ArrayList<>()
        );
    }


    public NewsResponse getNewsById(Long id) {
        User user = authenticateUser();
        News news = newsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("News with id: " + id + " not found!")
        );

        List<CommentResponse> commentResponses = commentRepository.getAllCommentResponsesByNewsId(news.getId());
        List<Favorite> favorites = favoriteRepository.findAll();
        for (Favorite fav : favorites) {
            if (fav.getNews().equals(news)) {
                if (fav.getUser().equals(user)) {
                    return new NewsResponse(
                            news.getId(),
                            news.getTitle(),
                            news.getDescription(),
                            news.getText(),
                            news.getCreatedAt(),
                            news.getImageCover(),
                            true,
                            commentResponses
                    );
                }
            }
        }

        return new NewsResponse(
                news.getId(),
                news.getTitle(),
                news.getDescription(),
                news.getText(),
                news.getCreatedAt(),
                news.getImageCover(),
                false,
                commentResponses
        );
    }

    public SimpleResponse deleteNewsById(Long id) {
        User user = authenticateUser();
        News news = newsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("News with id: " + id + " not found!")
        );

        if (!news.getUser().equals(user)) {
            throw new BadCredentialsException("You can not delete this news!");
        }

        newsRepository.delete(news);

        return new SimpleResponse("News with id: " + id + " successfully deleted!", "DELETE");
    }

    public NewsResponse changeNewsAction(Long id) {
        User user = authenticateUser();
        News news = newsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("News with id: " + id + " not found!")
        );

        List<Favorite> favorites = user.getFavorites();
        if (favorites != null) {
            for (Favorite fav : favorites) {
                if (fav.getNews().equals(news)) {
                    favoriteRepository.delete(fav);
                    return new NewsResponse(
                            news.getId(),
                            news.getTitle(),
                            news.getDescription(),
                            news.getText(),
                            news.getCreatedAt(),
                            news.getImageCover(),
                            false,
                            commentRepository.getAllCommentResponsesByNewsId(id)
                    );
                }
            }
        }

        Favorite favorite = new Favorite(news, user);
        favoriteRepository.save(favorite);

        return new NewsResponse(
                news.getId(),
                news.getTitle(),
                news.getDescription(),
                news.getText(),
                news.getCreatedAt(),
                news.getImageCover(),
                true,
                commentRepository.getAllCommentResponsesByNewsId(id)
        );
    }

    public List<NewsResponse> getAllNews() {
        User user = authenticateUser();
        List<News> news = newsRepository.findAll();
        List<NewsResponse> newsResponses = new ArrayList<>();
        for (News n : news) {
            NewsResponse newsResponse = new NewsResponse(n);
            if (user.getFavorites() != null) {
                for (Favorite fav : user.getFavorites()) {
                    if (fav.getNews().equals(n)) {
                        newsResponse.setIsFavorite(true);
                    }
                }
            } else {
                newsResponse.setIsFavorite(false);
            }

            newsResponse.setCommentResponses(commentRepository.getAllCommentResponsesByNewsId(n.getId()));
            newsResponses.add(newsResponse);
        }

        return newsResponses;
    }


}
