package ru.imanov.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.imanov.blog.entity.Comment;
import ru.imanov.blog.repository.CommentRepository;

import java.time.LocalDateTime;

/**
 * @author itimur
 */
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    /**
     * updates the comment in database
     * @param comment
     */
    public void updateComment(Comment comment){
        commentRepository.save(comment);
    }

    /**
     * Creates and saves a comment in the database
     * @param content - comment content
     * @param likesNumber - the number of likes of the comment
     * @param number - number of comments
     * @param createdDate - date of creation of the comment
     * @return created comment
     */
    public Comment createComment(String content, Short likesNumber, Long number, LocalDateTime createdDate){
        Comment comment = new Comment();

        comment.setContent(content);
        comment.setLikesNumber(likesNumber);
        comment.setNumber(number);
        comment.setCreatedDate(createdDate);

        commentRepository.save(comment);

        return comment;
    }

    /**
     * removes a сomment from the database
     * @param comment - comment to delete
     */
    public void deleteComment(Comment comment){
        commentRepository.delete(comment);
    }



}
