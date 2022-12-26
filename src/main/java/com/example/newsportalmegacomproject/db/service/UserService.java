package com.example.newsportalmegacomproject.db.service;

import com.example.newsportalmegacomproject.config.security.JwtUtil;
import com.example.newsportalmegacomproject.db.model.User;
import com.example.newsportalmegacomproject.db.repository.UserRepository;
import com.example.newsportalmegacomproject.dto.request.SignUpRequest;
import com.example.newsportalmegacomproject.dto.response.AuthResponse;
import com.example.newsportalmegacomproject.enums.Role;
import com.example.newsportalmegacomproject.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtTokenUtil;

    public AuthResponse registration(SignUpRequest request) {

        if (userRepository.existsByNickName(request.getNickName())) {
            throw new BadRequestException("This nick name: " + request.getNickName() + " is already in use!");
        }

        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getNickName(),
                passwordEncoder.encode(request.getPassword()),
                Role.USER
        );

        User save = userRepository.save(user);
        String jwt = jwtTokenUtil.generateToken(save.getNickName());
        return new AuthResponse(
                save.getId(),
                save.getFirstName(),
                save.getLastName(),
                save.getNickName(),
                jwt,
                save.getRole()
        );
    }
}
