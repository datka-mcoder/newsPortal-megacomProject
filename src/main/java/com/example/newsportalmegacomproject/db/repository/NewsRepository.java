package com.example.newsportalmegacomproject.db.repository;

import com.example.newsportalmegacomproject.db.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}