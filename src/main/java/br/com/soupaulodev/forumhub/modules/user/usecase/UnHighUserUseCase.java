package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UnauthorizedException;
import br.com.soupaulodev.forumhub.modules.user.repository.UserHighsRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service responsible for handling the logic of "unhighing" a user.
 * Allows a user to remove a previously given "high" to another user.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class UnHighUserUseCase {

    private final UserHighsRepository userHighsRepository;

    public UnHighUserUseCase(UserHighsRepository userHighsRepository) {
        this.userHighsRepository = userHighsRepository;
    }

    /**
     * Executes the logic for "unhighing" a user.
     *
     * @param unHighedUser the UUID of the user to be "unhighed"
     * @param authenticatedUserId the UUID of the authenticated user performing the "unhigh"
     * @throws IllegalArgumentException if:
     *         - the user tries to "unhigh" themselves,
     *         - there is no "high" relationship between the users.
     * @throws UnauthorizedException if the user is not authenticated.
     */
    public void execute(UUID unHighedUser, UUID authenticatedUserId) {
        if (authenticatedUserId == null) {
            throw new UnauthorizedException("User not authenticated");
        }

        if (unHighedUser.equals(authenticatedUserId)) {
            throw new IllegalArgumentException("User cannot unhigh himself");
        }

        userHighsRepository.findByHighedUser_IdAndHighingUser_Id(unHighedUser, authenticatedUserId)
                .ifPresentOrElse(userHighsRepository::delete, () -> {
                    throw new IllegalArgumentException("User not highed");
                });
    }
}
