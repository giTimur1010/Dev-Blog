package ru.imanov.blog.exception.tag;

import org.springframework.http.HttpStatus;
import ru.imanov.blog.exception.common.BaseException;

public class TagNotFoundException extends BaseException {
    public TagNotFoundException(String message, boolean isLogged) {
        super(message, isLogged, HttpStatus.NOT_FOUND);
    }
}
