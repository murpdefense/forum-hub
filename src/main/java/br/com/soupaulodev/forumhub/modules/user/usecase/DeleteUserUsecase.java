package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UserNotFoundException;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for deleting a user.
 */
@Service
public class DeleteUserUsecase {

    private final UserRepository userRepository;

    /**
     * Constructor for DeleteUserUsecase.
     *
     * @param userRepository the repository for user data
     */
    public DeleteUserUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Executes the use case to delete a user by their ID.
     *
     * @param id the unique identifier of the user to be deleted
     * @throws UserNotFoundException if no user with the given ID is found
     */
    public void execute(UUID id) {

        UserEntity userDB = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        userRepository.delete(userDB);
    }
}
