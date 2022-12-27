package com.example.newsportalmegacomproject.api;

import com.example.newsportalmegacomproject.db.service.UserService;
import com.example.newsportalmegacomproject.dto.request.SignInRequest;
import com.example.newsportalmegacomproject.dto.request.SignUpRequest;
import com.example.newsportalmegacomproject.dto.response.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/public")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Auth API", description = "Registration and authentication")
public class AuthAPI {

    private final UserService userService;

    @Operation(summary = "Sign up", description = "Any user can register")
    @PostMapping("/registration")
    public AuthResponse registration(@RequestBody SignUpRequest request) {
        return userService.registration(request);
    }

    @Operation(summary = "Sign in", description = "Only registered users can login")
    @PostMapping("/login")
    public AuthResponse login(@RequestBody SignInRequest request) {
        return userService.login(request);
    }
}
