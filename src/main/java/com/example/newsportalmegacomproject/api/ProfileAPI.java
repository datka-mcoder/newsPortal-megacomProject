package com.example.newsportalmegacomproject.api;

import com.example.newsportalmegacomproject.db.service.ProfileService;
import com.example.newsportalmegacomproject.dto.request.UpdateProfileImageRequest;
import com.example.newsportalmegacomproject.dto.request.UpdateProfileRequest;
import com.example.newsportalmegacomproject.dto.response.ProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/profile")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Profile API", description = "All endpoints of profile")
public class ProfileAPI {

    private final ProfileService profileService;

    @Operation(summary = "Get my profile", description = "Get my profile")
    @GetMapping("/me")
    public ProfileResponse getMyProfile() {
        return profileService.getMyProfile();
    }

    @Operation(summary = "Update profile", description = "Update profile by user token")
    @PutMapping
    public ProfileResponse updateProfile(@RequestBody UpdateProfileRequest request) {
        return profileService.updateProfile(request);
    }

    @Operation(summary = "Update profile image", description = "Update profile image")
    @PutMapping("/image")
    public ProfileResponse updateProfileImage(@RequestBody UpdateProfileImageRequest request) {
        return profileService.updateProfileImage(request);
    }
}
