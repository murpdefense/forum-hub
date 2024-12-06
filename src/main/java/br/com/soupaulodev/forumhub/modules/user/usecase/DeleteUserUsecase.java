package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UserNotFoundException;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case responsible for handling the deletion of a user by their ID.
 * <p>
 *     The {@link DeleteUserUsecase} class processes requests to delete a user by their unique identifier.
 *     If successful, the user is deleted from the database.
 * </p>
 * <p>
 *     It interacts with the {@link UserRepository} to delete user data from the database.
 *     If no user with the given ID is found, a {@link UserNotFoundException} is thrown.
 * </p>
 *
 * @author <a href="http://soupaulodev.com.br>soupaulodev</a>
 */
@Service
public class DeleteUserUsecase {

    private final UserRepository userRepository;

    /**
     * Constructs a new {@link DeleteUserUsecase}.
     *
     * @param userRepository the repository responsible for deleting user data from the database
     */
    public DeleteUserUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Executes the use case to delete a user by their ID.
     * <p>
     *     This method deletes a user from the database using the provided unique identifier.
     *     If the user is found, it is deleted from the database.
     *     If no user with the given ID is found, a {@link UserNotFoundException} is thrown.
     * </p>
     *
     * @param id the user's unique identifier of type {@link UUID}
     * @throws UserNotFoundException if no user with the given ID is found
     */
    public void execute(UUID id) {

        UserEntity userDB = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        userRepository.delete(userDB);
    }
}
