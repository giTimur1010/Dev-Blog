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
import ru.imanov.blog.rest.dto.request.user.NewUserRequest;
import ru.imanov.blog.rest.dto.request.user.UpdateUserRequest;
import ru.imanov.blog.rest.dto.response.user.NewUserResponse;
import ru.imanov.blog.rest.dto.response.user.UserAllFields;
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
     * @param request - user to update
     * @return - updated user
     * @throws UserNotFoundException - it is thrown out when the user is not in the database
     * @throws NullIdException - it is thrown out when the user has id = null
     */
    @Override
    public UserAllFields update(UpdateUserRequest request) throws UserNotFoundException, NullIdException {

        if (request.getId() == null){
            throw new NullIdException("you cannot update a user with id = null");
        }

        if (!userRepository.existsById(request.getId())){
            throw new UserNotFoundException(
                    String.format("the user with id = %d cannot be updated because it is not found", request.getId()),
                    true
            );
        }

        User user = User.builder()
                .avatarUrl(request.getAvatarUrl())
                .description(request.getDescription())
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .birthDate(request.getBirthDate())
                .build();

        user.setId(request.getId());

        // cannot be saved with a null password
        user.setPassword(userRepository.findById(user.getId()).get().getPassword());

        if (user.getBirthDate() != null && user.getBirthDate().isAfter(LocalDate.now())){
            throw new WrongDateException("the user's date of birth is incorrect");
        }

        checkUser(user);

        return transfrom(userRepository.save(user));
    }


    /**
     * adds a user
     * @param request - user to add
     * @return - added user
     * @throws UserAlreadyExistsException - it is thrown out when the user is already in the database
     */
    @Override
    public NewUserResponse add(NewUserRequest request) throws UserAlreadyExistsException {

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        checkUser(user);

        User addedUser = userRepository.save(user);

        return NewUserResponse.builder()
                .id(addedUser.getId())
                .email(addedUser.getEmail())
                .username(addedUser.getUsername())
                .build();
    }


    /**
     * finds a user by id
     * @param id - user id
     * @return - user found by id
     * @throws UserNotFoundException -  it is thrown out when the user is not in the database
     */
    @Override
    public UserAllFields getById(Long id) throws UserNotFoundException {
        Optional<User> userFromDb = userRepository.findById(id);

        if (userFromDb.isEmpty()){
            throw new UserNotFoundException(String.format("user not found by id = %d", id));
        }

        return transfrom(userFromDb.get());
    }

    /**
     * deletes a user by id
     * @param id - user to delete
     * @throws UserNotFoundException - it is thrown out when there is no user with the specified id
     */
    @Override
    public void delete(Long id) throws UserNotFoundException{
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
    }

    /**
     * transforms the User type into the UserAllFields type
     * @param user - user to transform
     * @return transformed user
     */
    private UserAllFields transfrom(User user){
        return UserAllFields.builder()
                .id(user.getId())
                .avatarUrl(user.getAvatarUrl())
                .description(user.getDescription())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .build();
    }
}
