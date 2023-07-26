package ru.imanov.blog.exception.common;

import org.springframework.http.HttpStatus;

public class NullIdException extends BaseException {
    public NullIdException(String message) {
        super(message, false, HttpStatus.BAD_REQUEST);
    }

    public NullIdException(String message, boolean isLogged) {
        super(message, isLogged, HttpStatus.BAD_REQUEST);
    }
}
