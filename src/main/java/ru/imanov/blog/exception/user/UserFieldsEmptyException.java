package ru.imanov.blog.exception.user;

import org.springframework.http.HttpStatus;
import ru.imanov.blog.exception.common.BaseException;

public class UserFieldsEmptyException extends BaseException {
    public UserFieldsEmptyException(String message, boolean isLogged) {
        super(message, isLogged, HttpStatus.BAD_REQUEST);
    }
}
