package ru.imanov.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.imanov.blog.entity.Article;
import ru.imanov.blog.entity.User;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    public List<Article> findAllByAuthor(User author);
}
