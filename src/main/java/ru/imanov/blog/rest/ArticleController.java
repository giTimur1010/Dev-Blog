package ru.imanov.blog.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.imanov.blog.entity.Article;
import ru.imanov.blog.service.ArticleService;

import java.util.List;

@RestController
@RequestMapping("/v1/api/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    ResponseEntity<Article> createArticle(@RequestBody Article article){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(articleService.add(article));
    }

    @GetMapping("/{id}")
    ResponseEntity<Article> GetArticleById(@PathVariable Long id){
        return ResponseEntity.ok(articleService.getById(id));
    }

    @GetMapping("/users/{id}")
    ResponseEntity<List<Article>> getAllArticlesByAuthorId(@PathVariable Long id){
        return ResponseEntity.ok(articleService.getAllUserArticles(id));
    }

    @PatchMapping
    ResponseEntity<Article> updateArticle(@RequestBody Article article){
        return ResponseEntity.ok(articleService.update(article));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteArticle(@PathVariable Long id){
        articleService.delete(id);
        return ResponseEntity.ok().build();
    }

}
