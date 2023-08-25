package ru.imanov.blog.rest.dto.request.user;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String username;

    private String email;

    private String password;
}
