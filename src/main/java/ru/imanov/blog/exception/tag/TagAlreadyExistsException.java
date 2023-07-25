package ru.imanov.blog.exception.tag;

import org.springframework.http.HttpStatus;
import ru.imanov.blog.exception.common.BaseException;

public class TagAlreadyExistsException extends BaseException {

    public TagAlreadyExistsException(String message) {
        super(message, false, HttpStatus.NOT_FOUND);
    }

    public TagAlreadyExistsException(String message, boolean isLogged) {
        super(message, isLogged, HttpStatus.NOT_FOUND);
    }
}
