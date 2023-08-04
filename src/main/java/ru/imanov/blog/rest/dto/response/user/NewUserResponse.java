package ru.imanov.blog.rest.dto.response.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewUserResponse {
    private Long id;

    private String username;

    private String email;
}
