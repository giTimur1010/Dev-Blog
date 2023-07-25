package ru.imanov.blog.exception.comment;

import org.springframework.http.HttpStatus;
import ru.imanov.blog.exception.common.BaseException;

public class CommentNotFoundException extends BaseException {
    public CommentNotFoundException(String message) {
        super(message, false, HttpStatus.BAD_REQUEST);
    }
    public CommentNotFoundException(String message, boolean isLogged) {
        super(message, isLogged, HttpStatus.BAD_REQUEST);
    }
}
