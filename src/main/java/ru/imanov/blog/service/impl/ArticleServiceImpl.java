package ru.imanov.blog.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.imanov.blog.entity.Article;
import ru.imanov.blog.exception.article.ArticleAlreadyExistsException;
import ru.imanov.blog.exception.article.ArticleFieldsEmptyException;
import ru.imanov.blog.exception.article.ArticleNotFoundException;
import ru.imanov.blog.exception.common.WrongDateException;
import ru.imanov.blog.repository.ArticleRepository;
import ru.imanov.blog.service.ArticleService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/**
 * @author itimur
 */

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    /**
     * updates the article
     * @param article - article to update
     * @return - updated article
     * @throws ArticleNotFoundException - it is thrown out when the article is not in the database
     */
    @Override
    public Article update(Article article) throws ArticleNotFoundException {
        if (articleRepository.existsById(article.getId())){
            throw new ArticleNotFoundException(
                    "the article cannot be updated because it is not in the database",
                    false
            );
        }

        checkArticle(article);

        return articleRepository.save(article);
    }

    /**
     * adds an article to the database
     * @param article - article to add
     * @return - the added article
     * @throws ArticleAlreadyExistsException - it is thrown out when the article is already in the database
     */
    @Override
    public Article add(Article article) throws ArticleAlreadyExistsException {
        if (article.getId() != null && articleRepository.existsById(article.getId())){
            throw new ArticleAlreadyExistsException("the article is already in the database", true);
        }

        checkArticle(article);

        return articleRepository.save(article);
    }


    /**
     * finds an article by id
     * @param id - article id
     * @return article found by id
     * @throws ArticleNotFoundException - it is thrown out when there is no article with the specified id
     */
    @Override
    public Article getById(Long id) throws ArticleNotFoundException {
        Optional<Article> articleFromDb = articleRepository.findById(id);
        if (articleFromDb.isEmpty()){
            throw new ArticleNotFoundException(
                    "the article cannot be updated because it is not in the database",
                    false
            );
        }

        return articleFromDb.get();
    }

    /**
     * finds all the user's articles by id
     * @param id - user id
     * @return - all user articles
     * @throws ArticleNotFoundException - it is thrown out when the user has no articles
     */
    @Override
    public List<Article> getAllUserArticles(Long id) throws ArticleNotFoundException{
        Optional<List<Article>> articlesFromDb = articleRepository.findAllByAuthorId(id);

        if (articlesFromDb.isEmpty()){
            throw new ArticleNotFoundException("The user has no articles", true);
        }

        return articlesFromDb.get();
    }

    /**
     * deletes the article by id
     * @param id -  article id
     */

    @Override
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    /**
     * checks whether all the necessary fields are filled in and whether the date
     * of creation of the article is correct
     * @param article - article to check
     * @throws ArticleFieldsEmptyException  -it is thrown out when not all the necessary
     * fields of the article are filled in
     * @throws WrongDateException - it is thrown out if the article creation date is incorrect
     */

    private void checkArticle(Article article) throws ArticleFieldsEmptyException, WrongDateException{
        if (
            StringUtils.isEmpty(article.getTitle()) ||
            StringUtils.isEmpty(article.getContent()) ||
            article.getAuthor() == null
        ) {
            throw new ArticleFieldsEmptyException("not all required fields are filled in in the article", true);
        }

        if (article.getCreatedDate() != null && article.getCreatedDate().isAfter(LocalDateTime.now())){
            throw new WrongDateException("wrong article created date", false);
        }
    }
}
