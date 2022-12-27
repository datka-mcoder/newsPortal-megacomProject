package com.example.newsportalmegacomproject.db.service;

import com.example.newsportalmegacomproject.db.repository.NewsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;


    public NewsResponse publishNews(NewsRequest request) {

    }
}
