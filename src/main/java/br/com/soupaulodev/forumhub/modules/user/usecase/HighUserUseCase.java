package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UnauthorizedException;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserHighsEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserHighsRepository;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service responsible for handling the logic of "highing" a user.
 * A user can give a "high" to another user as long as certain conditions are met.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class HighUserUseCase {

    private final UserHighsRepository userHighsRepository;
    private final UserRepository userRepository;

    public HighUserUseCase(UserHighsRepository userHighsRepository,
                           UserRepository userRepository) {
        this.userHighsRepository = userHighsRepository;
        this.userRepository = userRepository;
    }

    /**
     * Executes the logic for "highing" a user.
     *
     * @param highedUser the UUID of the user to be "highed"
     * @param authenticatedUserId the UUID of the authenticated user giving the "high"
     * @throws IllegalArgumentException if:
     *         - the user tries to "high" themselves,
     *         - the "high" has already been given,
     *         - either user is not found.
     * @throws UnauthorizedException if the user is not authenticated.
     */
    public void execute(UUID highedUser, UUID authenticatedUserId) {
        if (authenticatedUserId == null) {
            throw new UnauthorizedException("User not authenticated");
        }

        if (highedUser.equals(authenticatedUserId)) {
            throw new IllegalArgumentException("User cannot high himself");
        }

        userHighsRepository.findByHighedUser_IdAndHighingUser_Id(highedUser, authenticatedUserId)
                .ifPresent(userHighsEntity -> {
                    throw new IllegalArgumentException("User already highed");
                });

        UserEntity highedUserEntity = userRepository.findById(highedUser).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );

        UserEntity authenticatedUserEntity = userRepository.findById(authenticatedUserId).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );

        userHighsRepository.save(new UserHighsEntity(highedUserEntity, authenticatedUserEntity));
    }
}
