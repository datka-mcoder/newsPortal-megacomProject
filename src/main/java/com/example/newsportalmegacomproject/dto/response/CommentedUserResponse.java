package com.example.newsportalmegacomproject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentedUserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String image;
}
