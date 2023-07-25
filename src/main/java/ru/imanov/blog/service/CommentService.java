package ru.imanov.blog.service;

import ru.imanov.blog.entity.Article;
import ru.imanov.blog.entity.Comment;

import java.util.List;

public interface CommentService {
    Comment update(Comment comment);
    Comment add(Comment comment);
    Comment getById(Long id);
    List<Comment> getAllArticleComments(Long id);
    void delete(Long id);
}
