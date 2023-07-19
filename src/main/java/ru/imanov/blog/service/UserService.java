package ru.imanov.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.imanov.blog.entity.User;
import ru.imanov.blog.repository.UserRepository;

import java.time.LocalDate;

/**
 * @author itimur
 */

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Updates the user in the database
     * @param user - user to update
     */
    public void userUpdate(User user){
        userRepository.save(user);
    }

    /**
     * Creates and saves a user in the database
     * @param avatarUrl - the address where the user's avatar is located
     * @param description - user description
     * @param fullName - user full name
     * @param email - user email
     * @param password - user password
     * @param birthDate - user birth date
     * @return created user
     */
    public User createUser(String avatarUrl, String description, String username, String fullName, String email,
                           String password, LocalDate birthDate) {
        User user = new User();

        user.setAvatarUrl(avatarUrl);
        user.setDescription(description);
        user.setUsername(username);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirthDate(birthDate);

        userRepository.save(user);

        return user;
    }

    /**
     * removes a user from the database
     * @param user - user to delete
     */
    public void deleteUser(User user){
        userRepository.delete(user);
    }
}
