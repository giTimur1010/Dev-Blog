package ru.imanov.blog.exception.article;

import org.springframework.http.HttpStatus;
import ru.imanov.blog.exception.common.BaseException;

public class ArticleFieldsEmptyException extends BaseException {
    public ArticleFieldsEmptyException(String message, boolean isLogged) {
        super(message, isLogged, HttpStatus.BAD_REQUEST);
    }
}
