package com.example.newsportalmegacomproject.db.service;

import com.example.newsportalmegacomproject.config.security.JwtUtil;
import com.example.newsportalmegacomproject.db.model.Favorite;
import com.example.newsportalmegacomproject.db.model.News;
import com.example.newsportalmegacomproject.db.model.User;
import com.example.newsportalmegacomproject.db.repository.NewsRepository;
import com.example.newsportalmegacomproject.db.repository.UserRepository;
import com.example.newsportalmegacomproject.dto.request.UpdateProfileImageRequest;
import com.example.newsportalmegacomproject.dto.request.UpdateProfileRequest;
import com.example.newsportalmegacomproject.dto.response.AuthResponse;
import com.example.newsportalmegacomproject.dto.response.NewsResponse;
import com.example.newsportalmegacomproject.dto.response.ProfileResponse;
import com.example.newsportalmegacomproject.exceptions.BadRequestException;
import com.example.newsportalmegacomproject.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final JwtUtil jwtUtil;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByNickName(login).orElseThrow(
                () -> new NotFoundException("User with nick name: " + login + " not found!")
        );
    }

    public ProfileResponse getMyProfile() {
        User user = getAuthenticateUser();
        List<News> news = user.getNews();
        List<Favorite> userFavorites = user.getFavorites();
        List<News> userFavoriteNews = new ArrayList<>();
        ProfileResponse profileResponse = userRepository.getProfile(user.getNickName());
        List<NewsResponse> newsResponses = new ArrayList<>();
        if (userFavorites != null) {
            for (Favorite fav : userFavorites) {
                userFavoriteNews.add(fav.getNews());
            }
        }

        for (News n : news) {
            NewsResponse newsResponse = new NewsResponse(n);
            if (userFavoriteNews.contains(n)) {
                newsResponse.setIsFavorite(true);
                newsResponses.add(newsResponse);
            } else {
                newsResponse.setIsFavorite(false);
                newsResponses.add(newsResponse);
            }
        }

        profileResponse.setMyNewsResponses(newsResponses);
        return profileResponse;
    }

    public AuthResponse updateProfile(UpdateProfileRequest request) {
        User user = getAuthenticateUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        if (userRepository.existsByNickName(request.getNickName())) {
            throw new BadRequestException("Nick name: " + request.getNickName() + " already in use!");
        } else {
            user.setNickName(request.getNickName());
        }
        User save = userRepository.save(user);
        String jwt = jwtUtil.generateToken(save.getNickName());
        return new AuthResponse(
                save.getId(),
                save.getFirstName(),
                save.getLastName(),
                save.getNickName(),
                jwt,
                save.getRole()
        );
    }

    public ProfileResponse updateProfileImage(UpdateProfileImageRequest request) {
        User user = getAuthenticateUser();
        user.setImage(request.getImage());
        User save = userRepository.save(user);
        ProfileResponse profileResponse = userRepository.getProfile(save.getNickName());
        List<NewsResponse> myNewsResponses = newsRepository.getAllUserNewsResponsesSortedByIds(save.getNickName());
        profileResponse.setMyNewsResponses(myNewsResponses);
        return profileResponse;
    }
}
