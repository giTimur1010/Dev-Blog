package ru.imanov.blog.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.imanov.blog.entity.Tag;
import ru.imanov.blog.entity.User;
import ru.imanov.blog.service.TagService;

@RestController
@RequestMapping("/v1/api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(tagService.add(tag));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id){
        return ResponseEntity.ok(tagService.getById(id));
    }

    @PatchMapping
    public ResponseEntity<Tag> updateTag(@RequestBody Tag tag){
        return ResponseEntity.ok(tagService.update(tag));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTagById(@PathVariable Long id){
        tagService.delete(id);
        return ResponseEntity.ok().build();
    }

}

