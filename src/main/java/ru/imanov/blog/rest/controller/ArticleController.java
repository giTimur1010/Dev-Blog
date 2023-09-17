package ru.imanov.blog.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.imanov.blog.rest.dto.request.article.NewArticleRequest;
import ru.imanov.blog.rest.dto.request.article.UpdateArticleRequest;
import ru.imanov.blog.rest.dto.response.article.ArticleAllFields;
import ru.imanov.blog.rest.dto.response.article.NewArticleResponse;
import ru.imanov.blog.service.ArticleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<NewArticleResponse> createArticle(@RequestBody NewArticleRequest request){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(articleService.add(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<ArticleAllFields> GetArticleById(@PathVariable Long id){
        return ResponseEntity.ok(articleService.getById(id));
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<List<ArticleAllFields>> getAllArticlesByAuthorId(@PathVariable Long id){
        return ResponseEntity.ok(articleService.getAllUserArticles(id));
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ArticleAllFields> updateArticle(@RequestBody UpdateArticleRequest request){
        return ResponseEntity.ok(articleService.update(request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id){
        articleService.delete(id);
        return ResponseEntity.ok().build();
    }

}

