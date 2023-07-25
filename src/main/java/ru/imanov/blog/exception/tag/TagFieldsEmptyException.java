package ru.imanov.blog.exception.tag;

import org.springframework.http.HttpStatus;
import ru.imanov.blog.exception.common.BaseException;

public class TagFieldsEmptyException extends BaseException {

    public TagFieldsEmptyException(String message) {
        super(message, false, HttpStatus.BAD_REQUEST);
    }
    public TagFieldsEmptyException(String message, boolean isLogged) {
        super(message, isLogged, HttpStatus.BAD_REQUEST);
    }
}
