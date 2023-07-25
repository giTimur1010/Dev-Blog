package ru.imanov.blog.exception.article;

import org.springframework.http.HttpStatus;
import ru.imanov.blog.exception.common.BaseException;

public class ArticleAlreadyExistsException extends BaseException {
    public ArticleAlreadyExistsException(String message) {
        super(message, false, HttpStatus.BAD_REQUEST);
    }

    public ArticleAlreadyExistsException(String message, boolean isLogged){
        super(message, isLogged, HttpStatus.BAD_REQUEST);
    }
}
