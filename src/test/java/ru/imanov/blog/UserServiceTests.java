package ru.imanov.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.imanov.blog.entity.User;
import ru.imanov.blog.repository.UserRepository;
import ru.imanov.blog.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void updateUser(){
        Optional<User> userOptional = userRepository.findById(1L);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setFullName("Another full name");
            userService.userUpdate(user);
            assertEquals(userRepository.findById(1L).get().getFullName(), "Another full name");
        }
    }

}
