package com.example.newsportalmegacomproject.db.service;

import com.example.newsportalmegacomproject.db.model.News;
import com.example.newsportalmegacomproject.db.repository.NewsRepository;
import com.example.newsportalmegacomproject.dto.request.NewsRequest;
import com.example.newsportalmegacomproject.dto.response.FavoriteResponse;
import com.example.newsportalmegacomproject.dto.response.NewsResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;


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
}
