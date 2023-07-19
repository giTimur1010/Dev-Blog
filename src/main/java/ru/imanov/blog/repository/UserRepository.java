package ru.imanov.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.imanov.blog.entity.User;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
