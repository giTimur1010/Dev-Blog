package ru.imanov.blog.exception.user;

import org.springframework.http.HttpStatus;
import ru.imanov.blog.exception.common.BaseException;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(String message) {
        super(message, false, HttpStatus.NOT_FOUND);
    }

    public UserNotFoundException(String message, boolean isLogged) {
        super(message, isLogged, HttpStatus.NOT_FOUND);
    }
}
