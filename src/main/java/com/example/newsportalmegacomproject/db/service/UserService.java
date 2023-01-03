package com.example.newsportalmegacomproject.db.service;

import com.example.newsportalmegacomproject.config.security.JwtUtil;
import com.example.newsportalmegacomproject.db.model.User;
import com.example.newsportalmegacomproject.db.repository.UserRepository;
import com.example.newsportalmegacomproject.dto.request.SignInRequest;
import com.example.newsportalmegacomproject.dto.request.SignUpRequest;
import com.example.newsportalmegacomproject.dto.response.AuthResponse;
import com.example.newsportalmegacomproject.enums.Role;
import com.example.newsportalmegacomproject.exceptions.BadRequestException;
import com.example.newsportalmegacomproject.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    public AuthResponse login(SignInRequest request) {
        User user = userRepository.findByNickName(request.getNickName()).orElseThrow(
                () -> new NotFoundException("User with nick name: " + request.getNickName() + " not found!")
        );

        if (user.getPassword().isBlank()) {
            throw new BadRequestException("Password can not be empty!");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("incorrect password!");
        }

        String jwt = jwtTokenUtil.generateToken(user.getNickName());
        return new AuthResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getNickName(),
                jwt,
                user.getRole()
        );
    }
}
