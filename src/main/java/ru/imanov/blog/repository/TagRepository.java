package ru.imanov.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.imanov.blog.entity.Tag;


@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
