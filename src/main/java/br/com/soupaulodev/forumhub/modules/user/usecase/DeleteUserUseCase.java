package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForbiddenException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case responsible for handling the deletion of a user by their ID.
 * <p>
 * The {@link DeleteUserUseCase} class processes requests to delete a user by their unique identifier.
 * If successful, the user is deleted from the database.
 * </p>
 * <p>
 * It interacts with the {@link UserRepository} to delete user data from the database.
 * If no user with the given ID is found, a {@link ResourceNotFoundException} is thrown.
 * </p>
 *
 * @author <a href="http://soupaulodev.com.br>soupaulodev</a>
 */
@Service
public class DeleteUserUseCase {

    private final UserRepository userRepository;

    /**
     * Constructs a new {@link DeleteUserUseCase}.
     *
     * @param userRepository the repository responsible for deleting user data from the database
     */
    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Executes the use case to delete a user by their ID.
     * <p>
     * This method deletes a user from the database using the provided unique identifier.
     * If the user is found and user is authenticated, the user is deleted from the database.
     * If no user with the given ID is found, a {@link ResourceNotFoundException} is thrown.
     * </p>
     *
     * @param id                  the user's unique identifier of type {@link UUID}
     * @param authenticatedUserId the authenticated user's unique identifier
     * @throws ResourceNotFoundException if no user with the given ID is found
     * @throws ForbiddenException        if the authenticated user is not allowed to delete the user
     */
    public void execute(UUID id, UUID authenticatedUserId) {
        UserEntity userDB = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        if (!userDB.getId().equals(authenticatedUserId)) {
            throw new ForbiddenException("You are not allowed to delete this user.");
        }


        userRepository.delete(userDB);
    }
}
