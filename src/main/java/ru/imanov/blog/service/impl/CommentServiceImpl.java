package ru.imanov.blog.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.imanov.blog.entity.Article;
import ru.imanov.blog.entity.Comment;
import ru.imanov.blog.exception.article.ArticleNotFoundException;
import ru.imanov.blog.exception.comment.CommentFieldsEmptyException;
import ru.imanov.blog.exception.comment.CommentNotFoundException;
import ru.imanov.blog.exception.comment.CommentAlreadyExistsException;
import ru.imanov.blog.exception.common.NullIdException;
import ru.imanov.blog.exception.common.WrongDateException;
import ru.imanov.blog.exception.user.UserNotFoundException;
import ru.imanov.blog.repository.ArticleRepository;
import ru.imanov.blog.repository.CommentRepository;
import ru.imanov.blog.repository.UserRepository;
import ru.imanov.blog.rest.dto.request.comment.NewCommentRequest;
import ru.imanov.blog.rest.dto.request.comment.UpdateCommentRequest;
import ru.imanov.blog.rest.dto.response.comment.CommentAllFields;
import ru.imanov.blog.rest.dto.response.comment.NewCommentResponse;
import ru.imanov.blog.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author itimur
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final ArticleRepository articleRepository;

    /**
     * updates the comment
     * @param request - comment to update
     * @return - updated comment
     * @throws CommentNotFoundException - it is thrown out when the comment is not in the database
     * @throws NullIdException - it is thrown out when the comment has id = null or comment author has id = null
     * or article has id = null.
     */
    @Override
    public CommentAllFields update(UpdateCommentRequest request) throws CommentNotFoundException, NullIdException {

        if (request.getId() == null){
            throw new NullIdException("you cannot update a comment with id = null");
        }

        if (!commentRepository.existsById(request.getId())){
            throw new CommentNotFoundException(
                    String.format(
                            "the comment with id = %d cannot be updated because it is not in the database",
                            request.getId()
                    )
            );
        }

        if (!userRepository.existsById(request.getAuthorId())){
            throw new UserNotFoundException(
                    String.format("The comment cannot be updated with a author who is not in the database. Author id = %d",
                    request.getAuthorId())

            );
        }

        if (!articleRepository.existsById(request.getArticleId())){
            throw new ArticleNotFoundException(
                String.format(
                        "The comment cannot be updated with an article which is not in the database. Atricle id = %d",
                        request.getArticleId())
                );
        }

        Comment comment = Comment.builder()
                .author(userRepository.findById(request.getAuthorId()).get())
                .article(articleRepository.findById(request.getArticleId()).get())
                .content(request.getContent())
                .likesNumber(request.getLikesNumber())
                .number(request.getNumber())
                .createdDate(request.getCreatedDate())
                .build();

        comment.setId(request.getId());

        if (comment.getCreatedDate() != null && comment.getCreatedDate().isAfter(LocalDateTime.now())){
            throw new WrongDateException("wrong comment created date");
        }

        checkComment(comment);

        return transform(commentRepository.save(comment));
    }

    /**
     * adds a comment to the database
     * @param request - comment to add
     * @return - the added comment
     * @throws UserNotFoundException - it is thrown out when trying to add a comment with a non-existent user
     * @throws ArticleNotFoundException - it is thrown out when trying to add a comment with a non-existent article
     * @throws NullIdException - it is thrown out when thecomment author has id = null
     * or article has id = null.
     */
    @Override
    public NewCommentResponse add(NewCommentRequest request)
            throws UserNotFoundException, ArticleNotFoundException, NullIdException {

        if (request.getAuthorId() == null) {
            throw new NullIdException("a comment can't have an author with id = null");
        }

        if (request.getArticleId() == null){
            throw new NullIdException("a comment can't have an article with id = null");
        }

        if (!userRepository.existsById(request.getAuthorId())){
            throw new UserNotFoundException("The comment cannot be created with a user who is not in the database");
        }

        if (!articleRepository.existsById(request.getArticleId())){
            throw new UserNotFoundException("The comment cannot be created with an article which is not in the database");
        }

        Comment comment = Comment.builder()
                .author(userRepository.findById(request.getAuthorId()).get())
                .article(articleRepository.findById(request.getArticleId()).get())
                .content(request.getContent())
                .number(request.getNumber())
                .build();

        checkComment(comment);

        Comment addedComment = commentRepository.save(comment);

        return NewCommentResponse.builder()
                .id(addedComment.getId())
                .authorId(addedComment.getAuthor().getId())
                .articleId(addedComment.getArticle().getId())
                .content(addedComment.getContent())
                .number(addedComment.getNumber())
                .build();
    }


    /**
     * finds a comment by id
     * @param id - comment id
     * @return comment found by id
     * @throws CommentNotFoundException - it is thrown out when there is no comment with the specified id
     */
    @Override
    public CommentAllFields getById(Long id) throws CommentNotFoundException {
        Optional<Comment> commentFromDb = commentRepository.findById(id);

        if (commentFromDb.isEmpty()){
            throw new CommentNotFoundException(
                    String.format( "the comment with id = %d cannot be updated because it is not in the database", id)
            );
        }

        return transform(commentFromDb.get());
    }

    /**
     * finds all the articles comments by id
     * @param articleId - article id
     * @return - all article comments
     */
    @Override
    public List<CommentAllFields> getAllArticleComments(Long articleId) {
        return commentRepository.findAllByArticleId(articleId).stream()
                .map(this::transform)
                .toList();
    }

    /**
     * deletes the comment by id
     * @param id -  comment id
     * @throws CommentNotFoundException - it is thrown out when there is no comment with the specified id
     */
    @Override
    public void delete(Long id) throws CommentNotFoundException{

        if (!commentRepository.existsById(id)){
            throw new CommentNotFoundException(String.format(
                    "a tag with id = %d cannot be deleted because he is not in the database",
                    id
                )
            );
        }

        commentRepository.deleteById(id);
    }

    /**
     * checks whether all the necessary fields are filled in and whether the date
     * of creation of the comment is correct
     * @param comment - comment to check
     * @throws CommentFieldsEmptyException  -it is thrown out when not all the necessary
     * fields of the comment are filled in
     * @throws WrongDateException - it is thrown out if the comment creation date is incorrect
     */

    private void checkComment(Comment comment) throws CommentFieldsEmptyException, WrongDateException{
        if (
            StringUtils.isEmpty(comment.getContent()) ||
            comment.getNumber() == null ||
            comment.getAuthor() == null ||
            comment.getArticle() == null
        ) {
            throw new CommentFieldsEmptyException("not all required fields are filled in in the comment", true);
        }
    }

    /**
     * transforms the Comment type into the CommentAllFields type
     * @param comment - comment to transform
     * @return transformed comment
     */
    private CommentAllFields transform(Comment comment){
        return CommentAllFields.builder()
                .id(comment.getId())
                .authorId(comment.getAuthor().getId())
                .articleId(comment.getArticle().getId())
                .content(comment.getContent())
                .likesNumber(comment.getLikesNumber())
                .number(comment.getNumber())
                .createdDate(comment.getCreatedDate())
                .build();
    }
}
