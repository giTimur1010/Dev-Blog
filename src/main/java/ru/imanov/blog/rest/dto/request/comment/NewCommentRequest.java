package ru.imanov.blog.rest.dto.request.comment;


import lombok.Data;

@Data
public class NewCommentRequest {
    private Long authorId;

    private Long articleId;

    private String content;

    private Long number;
}
