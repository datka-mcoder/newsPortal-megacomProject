package com.example.newsportalmegacomproject.db.repository;

import com.example.newsportalmegacomproject.db.model.News;
import com.example.newsportalmegacomproject.dto.response.NewsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query("select n from News n order by n.id desc ")
    List<News> getAllNewsSortedByIds();

    @Query("select new com.example.newsportalmegacomproject.dto.response.NewsResponse(" +
            "m.id, " +
            "m.title, " +
            "m.description, " +
            "m.imageCover, " +
            "m.createdAt) from News m where m.user.nickName = :nickName order by m.id desc ")
    List<NewsResponse> getAllUserNewsResponsesSortedByIds(String nickName);
}