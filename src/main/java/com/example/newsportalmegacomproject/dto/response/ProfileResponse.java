package com.example.newsportalmegacomproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String nickName;
    private String image;
    private List<MyNewsResponse> myNewsResponses;

    public ProfileResponse(Long id, String firstName, String lastName, String nickName, String image) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.image = image;
    }
}
