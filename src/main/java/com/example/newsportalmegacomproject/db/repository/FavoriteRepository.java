package com.example.newsportalmegacomproject.db.repository;

import com.example.newsportalmegacomproject.db.model.Favorite;
import com.example.newsportalmegacomproject.dto.response.FavoriteResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query("select new com.example.newsportalmegacomproject.dto.response.FavoriteResponse(" +
            "f.id, " +
            "f.news.id, " +
            "f.news.title, " +
            "f.news.imageCover, " +
            "true) from Favorite f where f.id = :id order by f.id desc")
    FavoriteResponse getFavoriteResponse(Long id);
}