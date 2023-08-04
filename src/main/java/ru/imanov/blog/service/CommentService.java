package ru.imanov.blog.service;

import ru.imanov.blog.entity.Article;
import ru.imanov.blog.entity.Comment;
import ru.imanov.blog.rest.dto.request.comment.NewCommentRequest;
import ru.imanov.blog.rest.dto.request.comment.UpdateCommentRequest;
import ru.imanov.blog.rest.dto.response.comment.CommentAllFields;
import ru.imanov.blog.rest.dto.response.comment.NewCommentResponse;

import java.util.List;

public interface CommentService {
    CommentAllFields update(UpdateCommentRequest request);
    NewCommentResponse add(NewCommentRequest request);
    CommentAllFields getById(Long id);
    List<CommentAllFields> getAllArticleComments(Long id);
    void delete(Long id);
}
