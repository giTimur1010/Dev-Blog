package ru.imanov.blog.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.imanov.blog.entity.Article;
import ru.imanov.blog.service.ArticleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(articleService.add(article));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> GetArticleById(@PathVariable Long id){
        return ResponseEntity.ok(articleService.getById(id));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<Article>> getAllArticlesByAuthorId(@PathVariable Long id){
        return ResponseEntity.ok(articleService.getAllUserArticles(id));
    }

    @PatchMapping
    public ResponseEntity<Article> updateArticle(@RequestBody Article article){
        return ResponseEntity.ok(articleService.update(article));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id){
        articleService.delete(id);
        return ResponseEntity.ok().build();
    }

}

