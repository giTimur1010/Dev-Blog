package ru.imanov.blog.rest.dto.response.article;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ArticleAllFields {
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
