package ru.imanov.blog.service.impl;


import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import ru.imanov.blog.exception.common.NullIdException;
import ru.imanov.blog.exception.user.UserAlreadyExistsException;
import org.springframework.stereotype.Service;
import ru.imanov.blog.entity.User;
import ru.imanov.blog.exception.common.WrongDateException;
import ru.imanov.blog.exception.user.UserFieldsEmptyException;
import ru.imanov.blog.exception.user.UserNotFoundException;
import ru.imanov.blog.repository.UserRepository;
import ru.imanov.blog.service.UserService;

import java.time.LocalDate;
import java.util.Optional;


/**
 * @author itimur
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * updates the user
     * @param user - user to update
     * @return - updated user
     * @throws UserNotFoundException - it is thrown out when the user is not in the database
     * @throws NullIdException - it is thrown out when the user has id = null
     */
    @Override
    public User update(User user) throws UserNotFoundException, NullIdException {

        if (user.getId() == null){
            throw new NullIdException("you cannot update a user with id = null");
        }

        if (!userRepository.existsById(user.getId())){
            throw new UserNotFoundException(
                    String.format("the user with id = %d cannot be updated because it is not found", user.getId()),
                    true
            );
        }

        checkUser(user);

        return userRepository.save(user);
    }


    /**
     * adds a user
     * @param user - user to add
     * @return - added user
     * @throws UserAlreadyExistsException - it is thrown out when the user is already in the database
     */

    @Override
    public User add(User user) throws UserAlreadyExistsException {
        if (user.getId() != null && userRepository.existsById(user.getId())){
            throw new UserAlreadyExistsException(
                    String.format("the user with id = %d cannot be added as it already exists", user.getId()),
                    true
            );
        }

        checkUser(user);

        return userRepository.save(user);
    }


    /**
     * finds a user by id
     * @param id - user id
     * @return - user found by id
     * @throws UserNotFoundException -  it is thrown out when the user is not in the database
     */
    @Override
    public User getById(Long id) throws UserNotFoundException {
        Optional<User> userFromDb = userRepository.findById(id);

        if (userFromDb.isEmpty()){
            throw new UserNotFoundException(String.format("user not found by id = %d", id));
        }

        return userFromDb.get();
    }

    /**
     * deletes a user by id
     * @param id - user to delete
     */
    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)){
            throw new UserNotFoundException(String.format(
                        "a user with id = %d cannot be deleted because he is not in the database",
                        id
                    )
            );
        }

        userRepository.deleteById(id);
    }

    /**
     * checks that all necessary fields are filled in and the correctness of the date of birth
     * @param user - user to check
     * @throws UserFieldsEmptyException - it is thrown out when not all required user fields are filled in
     * @throws WrongDateException - it is thrown out when the user has an incorrect date of birth
     */
    private void checkUser(User user) throws UserFieldsEmptyException, WrongDateException{
        if (
            StringUtils.isEmpty(user.getUsername()) ||
            StringUtils.isEmpty(user.getEmail()) ||
            StringUtils.isEmpty(user.getPassword())
        ) {
            throw new UserFieldsEmptyException("not all required fields are filled in", true);
        }

        if (user.getBirthDate() != null && user.getBirthDate().isAfter(LocalDate.now())){
            throw new WrongDateException("the user's date of birth is incorrect");
        }
    }
}
