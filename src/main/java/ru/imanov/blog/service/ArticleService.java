package ru.imanov.blog.service;

import ru.imanov.blog.entity.Article;
import java.util.List;
public interface ArticleService {
    Article update(Article article);
    Article add(Article article);
    Article getById(Long id);
    List<Article> getAllUserArticles(Long id);
    void delete(Long id);
}
