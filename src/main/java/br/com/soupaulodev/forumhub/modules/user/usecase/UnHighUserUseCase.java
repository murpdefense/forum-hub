package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.user.repository.UserHighsRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case to unhigh a user
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class UnHighUserUseCase {

    private final UserHighsRepository userHighsRepository;

    /**
     * Constructor
     *
     * @param userHighsRepository User highs repository
     */
    public UnHighUserUseCase(UserHighsRepository userHighsRepository) {
        this.userHighsRepository = userHighsRepository;
    }

    /**
     * Execute the use case
     *
     * @param highedUser          Highed user
     * @param authenticatedUserId Authenticated user id
     */
    public void execute(UUID highedUser, UUID authenticatedUserId) {
        if (highedUser.equals(authenticatedUserId)) {
            throw new IllegalArgumentException("User cannot unhigh himself");
        }

        userHighsRepository.findByHighedUser_IdAndHighingUser_Id(highedUser, authenticatedUserId)
                .ifPresentOrElse(userHighsRepository::delete, () -> {
                    throw new IllegalArgumentException("User not highed");
                });
    }
}
