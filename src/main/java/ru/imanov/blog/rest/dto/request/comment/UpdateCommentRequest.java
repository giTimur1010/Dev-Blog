package ru.imanov.blog.rest.dto.request.comment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateCommentRequest {
    private Long id;

    private Long authorId;

    private Long articleId;

    private String content;

    private Short likesNumber;

    private Long number;

    private LocalDateTime createdDate;
}
