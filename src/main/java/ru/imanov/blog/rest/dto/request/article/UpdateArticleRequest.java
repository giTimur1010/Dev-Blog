package ru.imanov.blog.rest.dto.request.article;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UpdateArticleRequest {
    private Long id;

    private Long authorId;

    private String title;

    private String content;

    private Short likesNumber;

    private String thumbnailUrl;

    private LocalDateTime createdDate;

    private List<Long> commentsIds;

    private List<Long> tagsIds;
}
