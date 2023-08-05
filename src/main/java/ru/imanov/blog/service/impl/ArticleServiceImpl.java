package ru.imanov.blog.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.imanov.blog.entity.AbstractEntity;
import ru.imanov.blog.entity.Article;
import ru.imanov.blog.entity.Tag;
import ru.imanov.blog.entity.User;
import ru.imanov.blog.exception.article.ArticleFieldsEmptyException;
import ru.imanov.blog.exception.article.ArticleNotFoundException;
import ru.imanov.blog.exception.comment.CommentNotFoundException;
import ru.imanov.blog.exception.common.NullIdException;
import ru.imanov.blog.exception.common.WrongDateException;
import ru.imanov.blog.exception.tag.TagNotFoundException;
import ru.imanov.blog.exception.user.UserNotFoundException;
import ru.imanov.blog.repository.ArticleRepository;
import ru.imanov.blog.repository.CommentRepository;
import ru.imanov.blog.repository.TagRepository;
import ru.imanov.blog.repository.UserRepository;
import ru.imanov.blog.rest.dto.request.article.NewArticleRequest;
import ru.imanov.blog.rest.dto.request.article.UpdateArticleRequest;
import ru.imanov.blog.rest.dto.response.article.ArticleAllFields;
import ru.imanov.blog.rest.dto.response.article.NewArticleResponse;
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

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final TagRepository tagRepository;

    /**
     * updates the article
     * @param request - article to update
     * @return - updated article
     * @throws ArticleNotFoundException - it is thrown out when the article is not in the database
     * @throws NullIdException - it is thrown out when the article has id = null
     * @throws CommentNotFoundException - it is thrown out when there is no comment in the database
     * @throws TagNotFoundException - it is thrown out when there is no tag in the database
     */
    @Override
    public ArticleAllFields update(UpdateArticleRequest request)
            throws ArticleNotFoundException,  NullIdException, CommentNotFoundException, TagNotFoundException {
        if (request.getId() == null){
            throw new NullIdException("you cannot update a article with id = null");
        }

        if (!articleRepository.existsById(request.getId())){
            throw new ArticleNotFoundException(
                    String.format(
                            "the article with id = %d cannot be updated because it is not in the database",
                            request.getId()
                    )
            );
        }

        Optional<User> articleAuthor = userRepository.findById(request.getAuthorId());
        if (articleAuthor.isEmpty()){
            throw new UserNotFoundException (
                    "it is not possible to update an article with a user who is not in the database"
            );
        }

        if (request.getCommentsIds() != null) {
            for (Long commentId : request.getCommentsIds()){
                if (!commentRepository.existsById(commentId)) {
                    throw new CommentNotFoundException(
                            String.format(
                                    "it is not possible to update a tag with a non-existent comment. Comment id = %d",
                                    commentId
                            )
                    );
                }
            }
        }

        if (request.getTagsIds() != null) {
            for (Long tagId : request.getTagsIds()){
                if (!tagRepository.existsById(tagId)) {
                    throw new TagNotFoundException(
                            String.format(
                                    "it is not possible to update a tag with a non-existent comment. Comment id = %d",
                                    tagId
                            )
                    );
                }
            }
        }

        Article article = Article.builder()
                .author(articleAuthor.get())
                .title(request.getTitle())
                .content(request.getContent())
                .likesNumber(request.getLikesNumber())
                .thumbnailUrl(request.getThumbnailUrl())
                .createdDate(request.getCreatedDate())
                .build();

        article.setId(request.getId());

        article.setComments(
                request.getCommentsIds().stream()
                        .map(id -> commentRepository.findById(id).get())
                        .toList()
        );

        article.setTags(
                request.getTagsIds().stream()
                        .map(id -> tagRepository.findById(id).get())
                        .toList()
        );

        if (article.getCreatedDate() != null && article.getCreatedDate().isAfter(LocalDateTime.now())){
            throw new WrongDateException("wrong article created date");
        }

        checkArticle(article);

        return transform(articleRepository.save(article));
    }

    /**
     * adds an article to the database
     * @param request - article to add
     * @return - the added article
     * @throws UserNotFoundException - it is thrown out when trying to add an article with a non-existent user
     */
    @Override
    public NewArticleResponse add(NewArticleRequest request) throws UserNotFoundException {

        if (request.getAuthorId() == null){
            throw new NullIdException("the article cannot be created with an author whose id = null");
        }

        if (!userRepository.existsById(request.getAuthorId())){
            throw new UserNotFoundException(
                   String.format( "The article cannot be created with an author who is not in the database. Author id = %d",
                    request.getAuthorId())
            );
        }

        Article article = Article.builder()
                .author(userRepository.findById(request.getAuthorId()).get())
                .content(request.getContent())
                .title(request.getTitle())
                .build();

        checkArticle(article);

        Article addedArticle = articleRepository.save(article);

        return NewArticleResponse.builder()
                .id(addedArticle.getId())
                .authorId(addedArticle.getAuthor().getId())
                .content(addedArticle.getContent())
                .title(addedArticle.getTitle())
                .build();
    }


    /**
     * finds an article by id
     * @param id - article id
     * @return article found by id
     * @throws ArticleNotFoundException - it is thrown out when there is no article with the specified id
     */
    @Override
    public ArticleAllFields getById(Long id) throws ArticleNotFoundException {
        Optional<Article> articleFromDb = articleRepository.findById(id);

        if (articleFromDb.isEmpty()){
            throw new ArticleNotFoundException(
                    String.format("the article with id = %d cannot be updated because it is not in the database", id)
            );
        }

        return transform(articleFromDb.get());
    }


    /**
     * finds all the user's articles by id
     * @param authorId - user id
     * @return - all user articles
     * @throws NullIdException - it is thrown out when the author id = null
     */
    @Override
    public List<ArticleAllFields> getAllUserArticles(Long authorId) throws NullIdException {
        if (authorId == null) {
            throw new NullIdException("author id is null", true);
        }
        List<Article> articles = articleRepository.findAllByAuthorId(authorId);

        return articles.stream()
                .map(this::transform)
                .toList();
    }

    /**
     * deletes the article by id
     * @param id -  article id
     * @throws ArticleNotFoundException - it is thrown out when there is no article with the specified id
     */
    @Override
    public void delete(Long id) throws ArticleNotFoundException {
        if (!articleRepository.existsById(id)) {
            throw new UserNotFoundException(String.format(
                    "a tag with id = %d cannot be deleted because he is not in the database",
                    id
                )
            );
        }

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
    private void checkArticle(Article article) throws ArticleFieldsEmptyException, WrongDateException {
        if (
            StringUtils.isEmpty(article.getTitle()) ||
            StringUtils.isEmpty(article.getContent()) ||
            article.getAuthor() == null
        ) {
            throw new ArticleFieldsEmptyException("not all required fields are filled in the article", true);
        }
    }


    /**
     * transforms the Article type into the ArticleAllFields type
     * @param article - article to transform
     * @return transformed article
     */
    private ArticleAllFields transform(Article article) {
        ArticleAllFields articleAllFields = ArticleAllFields.builder()
                .id(article.getId())
                .authorId(article.getAuthor().getId())
                .title(article.getTitle())
                .content(article.getContent())
                .likesNumber(article.getLikesNumber())
                .thumbnailUrl(article.getThumbnailUrl())
                .createdDate(article.getCreatedDate())
                .build();

        if (article.getComments() != null){
            List<Long> commentIds = article.getComments().stream()
                    .map(AbstractEntity::getId)
                    .toList();

            articleAllFields.setCommentsIds(commentIds);
        }

        if (article.getTags() != null) {
            List<Long> tagsIds = article.getTags().stream()
                    .map(AbstractEntity::getId)
                    .toList();
            articleAllFields.setTagsIds(tagsIds);
        }

        return articleAllFields;
    }
}
