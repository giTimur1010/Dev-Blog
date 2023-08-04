package ru.imanov.blog.rest.dto.response.article;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewArticleResponse {
    private Long id;

    private Long authorId;

    private String title;

    private String content;
}
