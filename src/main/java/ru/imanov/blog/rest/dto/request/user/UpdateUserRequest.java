package ru.imanov.blog.rest.dto.request.user;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    private Long id;

    private String avatarUrl;

    private String description;

    private String username;

    private String fullName;

    private String email;

    private LocalDate birthDate;
}
