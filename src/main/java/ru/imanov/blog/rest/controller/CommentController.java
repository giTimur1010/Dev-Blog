package ru.imanov.blog.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.imanov.blog.entity.Comment;
import ru.imanov.blog.rest.dto.request.article.UpdateArticleRequest;
import ru.imanov.blog.rest.dto.request.comment.NewCommentRequest;
import ru.imanov.blog.rest.dto.request.comment.UpdateCommentRequest;
import ru.imanov.blog.rest.dto.response.comment.CommentAllFields;
import ru.imanov.blog.rest.dto.response.comment.NewCommentResponse;
import ru.imanov.blog.service.CommentService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<NewCommentResponse> createComment(@RequestBody NewCommentRequest request){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(commentService.add(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentAllFields> GetCommentById(@PathVariable Long id){
        return ResponseEntity.ok(commentService.getById(id));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<CommentAllFields>> getAllCommentsByArticleId(@PathVariable Long id){
        return ResponseEntity.ok(commentService.getAllArticleComments(id));
    }

    @PatchMapping
    public ResponseEntity<CommentAllFields> updateArticle(@RequestBody UpdateCommentRequest request){
        return ResponseEntity.ok(commentService.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id){
        commentService.delete(id);
        return ResponseEntity.ok().build();
    }

}
