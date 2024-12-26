package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.forum.repository.ForumHighsRepository;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case to unhigh a forum
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class UnHighForumUseCase {

    private final ForumHighsRepository forumHighsRepository;
    private final ForumRepository forumRepository;

    public UnHighForumUseCase(ForumHighsRepository forumHighsRepository,
                            ForumRepository forumRepository) {
        this.forumHighsRepository = forumHighsRepository;
        this.forumRepository = forumRepository;
    }

    /**
     * Use case to unhigh a forum
     *
     * @param forumId forum id
     * @param authenticatedUserId authenticated user id
     */
    public void execute(UUID forumId, UUID authenticatedUserId) {
        forumHighsRepository.findByForum_IdAndUser_Id(forumId, authenticatedUserId)
                .ifPresentOrElse(
                        forumHighsRepository::delete,
                    () -> {
                        throw new IllegalArgumentException("Forum not highed");
                    }
                );
        forumRepository.findById(forumId).ifPresent((forum) -> {
            forum.decrementHighs();
            forumRepository.save(forum);
        });
    }
}
