package ru.imanov.blog.exception.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class BaseException  extends RuntimeException {
    private final HttpStatus httpStatus;
    private final boolean isLogged;
    public BaseException(String message, boolean isLogged, HttpStatus httpStatus) {
        super(message);
        this.isLogged = isLogged;
        this.httpStatus = httpStatus;
    }
}
