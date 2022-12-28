package com.example.newsportalmegacomproject.api;

import com.example.newsportalmegacomproject.db.service.FavoriteService;
import com.example.newsportalmegacomproject.dto.response.FavoriteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/favorites")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Favorite API", description = "All endpoint of favorites")
public class FavoriteAPI {

    private final FavoriteService favoriteService;

    @Operation(summary = "Get all my favorites", description = "Get all my favorites")
    @GetMapping
    public List<FavoriteResponse> getAllMyFavorites() {
        return favoriteService.getAllMyFavorites();
    }
}
