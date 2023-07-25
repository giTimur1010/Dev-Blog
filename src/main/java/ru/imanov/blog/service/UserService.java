package ru.imanov.blog.service;
import ru.imanov.blog.entity.User;

public interface UserService {
    User  update(User user);
    User add(User user);
    User getById(Long id);
    void delete(Long id);

}
