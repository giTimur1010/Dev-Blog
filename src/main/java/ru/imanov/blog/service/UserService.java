package ru.imanov.blog.service;
import jakarta.servlet.Registration;
import ru.imanov.blog.entity.User;
import ru.imanov.blog.rest.dto.request.user.NewUserRequest;
import ru.imanov.blog.rest.dto.request.user.RegistrationRequest;
import ru.imanov.blog.rest.dto.request.user.UpdateUserRequest;
import ru.imanov.blog.rest.dto.response.user.NewUserResponse;
import ru.imanov.blog.rest.dto.response.user.UserAllFields;

public interface UserService {
    UserAllFields update(UpdateUserRequest request);
    NewUserResponse add(NewUserRequest request);
    void add(RegistrationRequest request);
    UserAllFields getById(Long id);
    void delete(Long id);

}
