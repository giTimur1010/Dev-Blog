package ru.imanov.blog.exception.comment;

import org.springframework.http.HttpStatus;
import ru.imanov.blog.exception.common.BaseException;

public class CommentAlreadyExistsException extends BaseException {
    public CommentAlreadyExistsException(String message, boolean isLogged) {
        super(message, isLogged, HttpStatus.BAD_REQUEST);
    }
}
