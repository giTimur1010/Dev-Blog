package ru.imanov.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.imanov.blog.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
