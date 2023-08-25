package ru.imanov.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.imanov.blog.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}

