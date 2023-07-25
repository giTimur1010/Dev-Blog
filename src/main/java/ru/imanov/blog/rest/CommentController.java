package ru.imanov.blog.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.imanov.blog.entity.Article;
import ru.imanov.blog.entity.Comment;
import ru.imanov.blog.service.CommentService;

import java.util.List;


@RestController
@RequestMapping("/v1/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    ResponseEntity<Comment> createComment(@RequestBody Comment comment){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(commentService.add(comment));
    }

    @GetMapping("/{id}")
    ResponseEntity<Comment> GetCommentById(@PathVariable Long id){
        return ResponseEntity.ok(commentService.getById(id));
    }

    @GetMapping("/users/{id}")
    ResponseEntity<List<Comment>> getAllCommentsByArticleId(@PathVariable Long id){
        return ResponseEntity.ok(commentService.getAllArticleComments(id));
    }

    @PatchMapping
    ResponseEntity<Comment> updateArticle(@RequestBody Comment comment){
        return ResponseEntity.ok(commentService.update(comment));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteArticle(@PathVariable Long id){
        commentService.delete(id);
        return ResponseEntity.ok().build();
    }

}
