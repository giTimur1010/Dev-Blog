package ru.imanov.blog.service;

import ru.imanov.blog.entity.Article;
import ru.imanov.blog.rest.dto.request.article.NewArticleRequest;
import ru.imanov.blog.rest.dto.request.article.UpdateArticleRequest;
import ru.imanov.blog.rest.dto.response.article.ArticleAllFields;
import ru.imanov.blog.rest.dto.response.article.NewArticleResponse;

import java.util.List;
public interface ArticleService {
    ArticleAllFields update(UpdateArticleRequest request);
    NewArticleResponse add(NewArticleRequest request);
    ArticleAllFields getById(Long id);
    List<ArticleAllFields> getAllUserArticles(Long id);
    void delete(Long id);
}
