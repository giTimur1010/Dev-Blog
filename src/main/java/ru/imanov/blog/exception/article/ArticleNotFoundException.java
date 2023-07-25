package ru.imanov.blog.exception.article;

import org.springframework.http.HttpStatus;
import ru.imanov.blog.exception.common.BaseException;

public class ArticleNotFoundException extends BaseException {
    public ArticleNotFoundException(String message) {
        super(message, false, HttpStatus.NOT_FOUND);
    }

    public ArticleNotFoundException(String message, boolean isLogged) {
        super(message, isLogged, HttpStatus.NOT_FOUND);
    }
}
