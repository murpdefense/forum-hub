package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumHighsEntity;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumHighsRepository;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case to high a forum
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class HighForumUseCase {

    private final ForumHighsRepository forumHighsRepository;
    private final ForumRepository forumRepository;
    private final UserRepository userRepository;

    public HighForumUseCase(ForumHighsRepository forumHighsRepository,
                            ForumRepository forumRepository,
                            UserRepository userRepository) {
        this.forumHighsRepository = forumHighsRepository;
        this.forumRepository = forumRepository;
        this.userRepository = userRepository;
    }

    public void execute(UUID forumId, UUID authenticatedUserId) {
        forumHighsRepository.findByForum_IdAndUser_Id(forumId, authenticatedUserId)
                .ifPresent(userHighsEntity -> {
                    throw new IllegalArgumentException("Forum already highed");
                });

        ForumEntity forumFound = forumRepository.findById(forumId).orElseThrow(
                () -> new IllegalArgumentException("Forum not found")
        );

        UserEntity authenticatedUserEntity = userRepository.findById(authenticatedUserId).orElseThrow(
                () -> new IllegalArgumentException("Forum not found")
        );

        forumHighsRepository.save(new ForumHighsEntity(forumFound, authenticatedUserEntity));
    }
}
