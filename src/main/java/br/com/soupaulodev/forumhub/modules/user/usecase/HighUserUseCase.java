package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserHighsEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserHighsRepository;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HighUserUseCase {

    private final UserHighsRepository userHighsRepository;
    private final UserRepository userRepository;

    public HighUserUseCase(UserHighsRepository userHighsRepository,
                           UserRepository userRepository) {
        this.userHighsRepository = userHighsRepository;
        this.userRepository = userRepository;
    }

    public void execute(UUID highedUser, UUID authenticatedUserId) {
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
