package ru.imanov.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleEnum {
    USER("USER"),
    ADMIN("ADMIN");
    private final String name;
}
