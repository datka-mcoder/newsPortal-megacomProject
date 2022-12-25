package com.example.newsportalmegacomproject.db.repository;

import com.example.newsportalmegacomproject.db.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}