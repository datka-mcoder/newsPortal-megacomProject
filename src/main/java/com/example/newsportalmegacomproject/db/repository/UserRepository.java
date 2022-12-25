package com.example.newsportalmegacomproject.db.repository;

import com.example.newsportalmegacomproject.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}