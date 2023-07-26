package ru.imanov.blog.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.imanov.blog.entity.Comment;
import ru.imanov.blog.exception.comment.CommentFieldsEmptyException;
import ru.imanov.blog.exception.comment.CommentNotFoundException;
import ru.imanov.blog.exception.comment.CommentAlreadyExistsException;
import ru.imanov.blog.exception.common.NullIdException;
import ru.imanov.blog.exception.common.WrongDateException;
import ru.imanov.blog.exception.user.UserNotFoundException;
import ru.imanov.blog.repository.CommentRepository;
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

    /**
     * updates the comment
     * @param comment - comment to update
     * @return - updated comment
     * @throws CommentNotFoundException - it is thrown out when the comment is not in the database
     * @throws NullIdException - it is thrown out when the comment has id = null
     */
    @Override
    public Comment update(Comment comment) throws CommentNotFoundException, NullIdException {

        if (comment.getId() == null){
            throw new NullIdException("you cannot update a comment with id = null");
        }

        if (!commentRepository.existsById(comment.getId())){
            throw new CommentNotFoundException(
                    String.format(
                            "the comment with id = %d cannot be updated because it is not in the database",
                            comment.getId()
                    )
            );
        }

        checkComment(comment);

        return commentRepository.save(comment);
    }

    /**
     * adds a comment to the database
     * @param comment - comment to add
     * @return - the added comment
     * @throws CommentAlreadyExistsException - it is thrown out when the comment is already in the database
     */
    @Override
    public Comment add(Comment comment) throws CommentAlreadyExistsException {
        if (comment.getId() != null && commentRepository.existsById(comment.getId())){
            throw new CommentAlreadyExistsException(
                    String.format("the comment with id = %d is already in the database", comment.getId()),
                    true
            );
        }

        checkComment(comment);

        return commentRepository.save(comment);
    }


    /**
     * finds a comment by id
     * @param id - comment id
     * @return comment found by id
     * @throws CommentNotFoundException - it is thrown out when there is no comment with the specified id
     */
    @Override
    public Comment getById(Long id) throws CommentNotFoundException {
        Optional<Comment> commentFromDb = commentRepository.findById(id);

        if (commentFromDb.isEmpty()){
            throw new CommentNotFoundException(
                    String.format( "the comment with id = %d cannot be updated because it is not in the database", id)
            );
        }

        return commentFromDb.get();
    }

    /**
     * finds all the articles comments by id
     * @param articleId - article id
     * @return - all article comments
     */
    @Override
    public List<Comment> getAllArticleComments(Long articleId) {
        return commentRepository.findAllByArticleId(articleId);
    }

    /**
     * deletes the comment by id
     * @param id -  comment id
     */
    @Override
    public void delete(Long id) {

        if (!commentRepository.existsById(id)){
            throw new UserNotFoundException(String.format(
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

        if (comment.getCreatedDate() != null && comment.getCreatedDate().isAfter(LocalDateTime.now())){
            throw new WrongDateException("wrong comment created date");
        }
    }
}
