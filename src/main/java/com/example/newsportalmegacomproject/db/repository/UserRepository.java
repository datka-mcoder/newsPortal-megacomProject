package com.example.newsportalmegacomproject.db.repository;

import com.example.newsportalmegacomproject.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.nickName = :nickName")
    Optional<User> findByNickName(String nickName);

    @Query("select case when count(u)>0 then true else false end from User u where u.nickName like :nickName")
    boolean existsByNickName(@Param(value = "nickName") String nickName);
}