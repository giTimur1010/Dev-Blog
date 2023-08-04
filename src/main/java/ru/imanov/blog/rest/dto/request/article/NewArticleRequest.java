package ru.imanov.blog.rest.dto.request.article;

import lombok.Data;

@Data
public class NewArticleRequest {
    private Long authorId;

    private String title;

    private String content;
}
