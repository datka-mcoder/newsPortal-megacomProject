package com.example.newsportalmegacomproject.db.repository;

import com.example.newsportalmegacomproject.db.model.AuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthInfoRepository extends JpaRepository<AuthInfo, Long> {
}