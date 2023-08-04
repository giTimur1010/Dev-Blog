package ru.imanov.blog.rest.dto.response.comment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewCommentResponse {
    private Long id;

    private Long authorId;

    private Long articleId;

    private String content;

    private Long number;
}
