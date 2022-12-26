package com.example.newsportalmegacomproject.dto.response;

import com.example.newsportalmegacomproject.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String nickName;
    private String jwt;
    private Role role;
}
