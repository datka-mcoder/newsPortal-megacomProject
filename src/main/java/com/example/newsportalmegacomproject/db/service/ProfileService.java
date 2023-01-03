package com.example.newsportalmegacomproject.db.service;

import com.example.newsportalmegacomproject.db.model.User;
import com.example.newsportalmegacomproject.db.repository.NewsRepository;
import com.example.newsportalmegacomproject.db.repository.UserRepository;
import com.example.newsportalmegacomproject.dto.request.UpdateProfileImageRequest;
import com.example.newsportalmegacomproject.dto.request.UpdateProfileRequest;
import com.example.newsportalmegacomproject.dto.response.NewsResponse;
import com.example.newsportalmegacomproject.dto.response.ProfileResponse;
import com.example.newsportalmegacomproject.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final NewsRepository newsRepository;

    private User getAuthenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByNickName(login).orElseThrow(
                () -> new NotFoundException("User with nick name: " + login + " not found!")
        );
    }

    public ProfileResponse getMyProfile() {
        User user = getAuthenticateUser();
        List<NewsResponse> news = newsRepository.getAllUserNewsResponsesSortedByIds(user.getNickName());
        ProfileResponse profileResponse = userRepository.getProfile(user.getNickName());
        profileResponse.setMyNewsResponses(news);
        return profileResponse;
    }

    public ProfileResponse updateProfile(UpdateProfileRequest request) {
        User user = getAuthenticateUser();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setNickName(request.getNickName());
        User save = userRepository.save(user);
        return userRepository.getProfile(save.getNickName());
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
