package ru.imanov.blog.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.imanov.blog.entity.User;
import ru.imanov.blog.rest.dto.request.user.NewUserRequest;
import ru.imanov.blog.rest.dto.request.user.UpdateUserRequest;
import ru.imanov.blog.rest.dto.response.user.NewUserResponse;
import ru.imanov.blog.rest.dto.response.user.UserAllFields;
import ru.imanov.blog.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<NewUserResponse> createUser(@RequestBody NewUserRequest request){
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(userService.add(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAllFields> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getById(id));
    }

    @PatchMapping
    public ResponseEntity<UserAllFields> updateUser(@RequestBody UpdateUserRequest user){
        return ResponseEntity.ok(userService.update(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
