package com.example.newsportalmegacomproject.db.service;

import com.example.newsportalmegacomproject.db.model.Favorite;
import com.example.newsportalmegacomproject.db.model.User;
import com.example.newsportalmegacomproject.db.repository.FavoriteRepository;
import com.example.newsportalmegacomproject.db.repository.UserRepository;
import com.example.newsportalmegacomproject.dto.response.FavoriteResponse;
import com.example.newsportalmegacomproject.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByNickName(login).orElseThrow(
                () -> new NotFoundException("User with nick name: " + login + " not found!")
        );
    }

    public List<FavoriteResponse> getAllMyFavorites() {
        User user = getAuthenticateUser();
        List<Favorite> userFavorites = user.getFavorites();
        List<FavoriteResponse> favoriteResponses = new ArrayList<>();
        for (Favorite fav : userFavorites) {
            favoriteResponses.add(favoriteRepository.getFavoriteResponse(fav.getId()));
        }

        return favoriteResponses;
    }
}
