package ru.imanov.blog.exception.user;

import org.springframework.http.HttpStatus;
import ru.imanov.blog.exception.common.BaseException;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException(String message) {
        super(message, false, HttpStatus.BAD_REQUEST);
    }

    public UserAlreadyExistsException(String message, boolean isLogged) {
        super(message, isLogged, HttpStatus.BAD_REQUEST);
    }
}
