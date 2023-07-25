package ru.imanov.blog.exception.common;

import org.springframework.http.HttpStatus;

public class WrongDateException extends BaseException{
    public WrongDateException(String message, boolean isLogged) {
        super(message, isLogged, HttpStatus.BAD_REQUEST);
    }
}
