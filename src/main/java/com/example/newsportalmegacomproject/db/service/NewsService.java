package com.example.newsportalmegacomproject.db.service;

import com.example.newsportalmegacomproject.db.model.*;
import com.example.newsportalmegacomproject.db.repository.CommentRepository;
import com.example.newsportalmegacomproject.db.repository.FavoriteRepository;
import com.example.newsportalmegacomproject.db.repository.NewsRepository;
import com.example.newsportalmegacomproject.db.repository.UserRepository;
import com.example.newsportalmegacomproject.dto.request.FilterNewsCategoryRequest;
import com.example.newsportalmegacomproject.dto.request.NewsRequest;
import com.example.newsportalmegacomproject.dto.response.*;
import com.example.newsportalmegacomproject.enums.Category;
import com.example.newsportalmegacomproject.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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


    public NewsInnerResponsePage publishNews(NewsRequest request) {
        User user = authenticateUser();
        News news = new News(request);
        news.setUser(user);
        News save = newsRepository.save(news);
        return new NewsInnerResponsePage(
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


    public NewsInnerResponsePage getNewsById(Long id) {
        User user = authenticateUser();
        News news = newsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("News with id: " + id + " not found!")
        );

        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment com : news.getComments()) {
            CommentedUserResponse commentedUserResponse = userRepository.getCommentedUser(com.getUser().getId());
            CommentResponse commentResponse = new CommentResponse(com, commentedUserResponse);
            List<ReplyCommentResponse> replyCommentResponses = new ArrayList<>();
            for (ReplyComment r : com.getReplyComments()) {
                CommentedUserResponse replyCommentedUserResponse = userRepository.getCommentedUser(r.getUser().getId());
                ReplyCommentResponse replyCommentResponse = new ReplyCommentResponse(r, replyCommentedUserResponse);
                replyCommentResponses.add(replyCommentResponse);
            }

            commentResponse.setReplyCommentResponses(replyCommentResponses);
            commentResponses.add(commentResponse);
        }

        List<Favorite> favorites = favoriteRepository.findAll();
        for (Favorite fav : favorites) {
            if (fav.getNews().equals(news)) {
                if (fav.getUser().equals(user)) {
                    return new NewsInnerResponsePage(
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

        return new NewsInnerResponsePage(
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

    public NewsInnerResponsePage changeNewsAction(Long id) {
        User user = authenticateUser();
        News news = newsRepository.findById(id).orElseThrow(
                () -> new NotFoundException("News with id: " + id + " not found!")
        );

        List<Favorite> favorites = user.getFavorites();
        if (favorites != null) {
            for (Favorite fav : favorites) {
                if (fav.getNews().equals(news)) {
                    favoriteRepository.delete(fav);
                    return new NewsInnerResponsePage(
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

        return new NewsInnerResponsePage(
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
        List<News> news = newsRepository.getAllNewsSortedByIds();
        List<NewsResponse> newsResponses = new ArrayList<>();
        List<News> allNews = new ArrayList<>();
        List<Favorite> userFavorites = user.getFavorites();
        for (Favorite fav : userFavorites) {
            allNews.add(fav.getNews());
        }

        for (News n : news) {
            NewsResponse newsResponse = new NewsResponse(n);
            if (allNews.contains(n)) {
                for (Favorite fav : user.getFavorites()) {
                    if (fav.getNews().equals(n)) {
                        newsResponse.setIsFavorite(true);
                    }
                }
            } else {
                newsResponse.setIsFavorite(false);
            }

            newsResponses.add(newsResponse);
        }

        return newsResponses;
    }

    public List<NewsResponse> getAllMyPublications() {
        User user = authenticateUser();
        List<News> news = userRepository.getAllMyPublicationsSortedByIds(user.getNickName());
        List<NewsResponse> newsResponses = new ArrayList<>();
        List<News> favoriteNews = new ArrayList<>();
        List<Favorite> userFavorites = user.getFavorites();
        for (Favorite fav : userFavorites) {
            favoriteNews.add(fav.getNews());
        }

        for (News n : news) {
            NewsResponse newsResponse = new NewsResponse(n);
            if (favoriteNews.contains(n)) {
                for (Favorite fav : user.getFavorites()) {
                    if (fav.getNews().equals(n)) {
                        newsResponse.setIsFavorite(true);
                    }
                }
            } else {
                newsResponse.setIsFavorite(false);
            }

            newsResponses.add(newsResponse);
        }

        return newsResponses;
    }

    public List<NewsResponse> filterNewsByCategory(FilterNewsCategoryRequest request) {
        List<NewsResponse> filter = new ArrayList<>();
        User user = authenticateUser();
        List<News> news = newsRepository.findAll();
        List<News> filteredNews = new ArrayList<>();
        for (News n : news) {
            for (Category c : request.getCategories()) {
                if (n.getCategory().equals(c)) {
                    filteredNews.add(n);
                }
            }
        }

        List<Favorite> userFavorite = user.getFavorites();
        List<News> userFavoriteNews = new ArrayList<>();
        for (Favorite fav : userFavorite) {
            userFavoriteNews.add(fav.getNews());
        }

        if (filteredNews.size() > userFavoriteNews.size()) {
            for (News n : filteredNews) {
                if (userFavoriteNews.contains(n)) {
                    NewsResponse newsResponse = new NewsResponse(
                            n.getId(),
                            n.getTitle(),
                            n.getDescription(),
                            n.getImageCover(),
                            n.getCreatedAt(),
                            true);
                    filter.add(newsResponse);
                } else {
                    NewsResponse newsResponse = new NewsResponse(
                            n.getId(),
                            n.getTitle(),
                            n.getDescription(),
                            n.getImageCover(),
                            n.getCreatedAt(),
                            false);
                    filter.add(newsResponse);
                }
            }
        } else {
            for (News n : userFavoriteNews) {
                if (filteredNews.contains(n)) {
                    NewsResponse newsResponse = new NewsResponse(
                            n.getId(),
                            n.getTitle(),
                            n.getDescription(),
                            n.getImageCover(),
                            n.getCreatedAt(),
                            true);
                    filter.add(newsResponse);
                } else {
                    NewsResponse newsResponse = new NewsResponse(
                            n.getId(),
                            n.getTitle(),
                            n.getDescription(),
                            n.getImageCover(),
                            n.getCreatedAt(),
                            false);
                    filter.add(newsResponse);
                }
            }
        }

        return filter;
    }
}
