package ru.imanov.blog.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.imanov.blog.entity.Tag;
import ru.imanov.blog.entity.User;
import ru.imanov.blog.rest.dto.request.tag.NewTagRequest;
import ru.imanov.blog.rest.dto.request.tag.UpdateTagRequest;
import ru.imanov.blog.rest.dto.response.tag.NewTagResponse;
import ru.imanov.blog.rest.dto.response.tag.TagAllFields;
import ru.imanov.blog.service.TagService;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<NewTagResponse> createTag(@RequestBody NewTagRequest request){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(tagService.add(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<TagAllFields> getTagById(@PathVariable Long id){
        return ResponseEntity.ok(tagService.getById(id));
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TagAllFields> updateTag(@RequestBody UpdateTagRequest request){
        return ResponseEntity.ok(tagService.update(request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteTagById(@PathVariable Long id){
        tagService.delete(id);
        return ResponseEntity.ok().build();
    }

}

