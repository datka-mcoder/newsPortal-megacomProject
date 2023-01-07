package com.example.newsportalmegacomproject.api;

import com.example.newsportalmegacomproject.db.repository.NewsRepository;
import com.example.newsportalmegacomproject.db.service.NewsService;
import com.example.newsportalmegacomproject.dto.request.NewsRequest;
import com.example.newsportalmegacomproject.dto.response.NewsResponse;
import com.example.newsportalmegacomproject.dto.response.NewsInnerResponsePage;
import com.example.newsportalmegacomproject.dto.response.SimpleResponse;
import com.example.newsportalmegacomproject.enums.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/news")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "News API", description = "All endpoints of News")
public class NewsAPI {

    private final NewsService newsService;

    @Operation(summary = "Publish news", description = "Publish news")
    @PostMapping
    public NewsInnerResponsePage publishNews(@RequestBody NewsRequest request) {
        return newsService.publishNews(request);
    }

    @Operation(summary = "Get news", description = "Get news by id")
    @GetMapping("/{id}")
    public NewsInnerResponsePage getNewsById(@PathVariable Long id) {
        return newsService.getNewsById(id);
    }

    @Operation(summary = "Delete news", description = "Delete news by id")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteNewsById(@PathVariable Long id) {
        return newsService.deleteNewsById(id);
    }

    @Operation(summary = "Change news action", description = "Change news action by id")
    @PutMapping("/{id}")
    public NewsInnerResponsePage changeNewsAction(@PathVariable Long id) {
        return newsService.changeNewsAction(id);
    }

    @Operation(summary = "Get all news", description = "Get all news")
    @GetMapping
    public List<NewsResponse> getAllNews() {
        return newsService.getAllNews();
    }

    @Operation(summary = "Get all my publications", description = "Get all my publications")
    @GetMapping("/my-publications")
    public List<NewsResponse> getAllMyPublications() {
        return newsService.getAllMyPublications();
    }

    @Operation(summary = "Filter news by category", description = "Filter news by category")
    @GetMapping("/filter")
    public List<NewsRepository> filterNewsByCategory(@RequestBody List<Category> categories) {
        return newsService.filterNewsByCategory(categories);
    }
}
