package ru.imanov.blog.rest.dto.request.user;

import lombok.Data;

@Data
public class NewUserRequest {
    private String username;

    private String email;

    private String password;
}
