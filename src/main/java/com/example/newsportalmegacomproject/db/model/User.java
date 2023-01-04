package com.example.newsportalmegacomproject.db.model;

import com.example.newsportalmegacomproject.dto.request.UpdateProfileRequest;
import com.example.newsportalmegacomproject.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    private String firstName;

    private String lastName;

    private String nickName;

    private String password;

    private String image;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(cascade = ALL, mappedBy = "user")
    private List<News> news;

    @OneToMany(cascade = {ALL}, mappedBy = "user")
    private List<Favorite> favorites;

    @OneToMany(cascade = {ALL})
    private List<Comment> comments;

    public User(String firstName, String lastName, String nickName, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.password = password;
        this.role = role;
    }

    public User(UpdateProfileRequest request) {
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
        this.nickName = request.getNickName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.nickName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
