package ru.imanov.blog.rest.dto.response.comment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentAllFields {
    private Long id;

    private Long authorId;

    private Long articleId;

    private String content;

    private Short likesNumber;

    private Long number;

    private LocalDateTime createdDate;
}
