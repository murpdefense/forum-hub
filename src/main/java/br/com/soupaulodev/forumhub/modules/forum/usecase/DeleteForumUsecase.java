package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumNotFoundException;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case for deleting a forum.
 */
@Service
public class DeleteForumUsecase {

    private final ForumRepository forumRepository;

    /**
     * Constructs a new DeleteForumUsecase with the specified repository.
     *
     * @param forumRepository the repository for managing forums
     */
    public DeleteForumUsecase(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    /**
     * Executes the use case to delete a forum by its unique identifier.
     *
     * @param id the unique identifier of the forum to be deleted
     * @throws ForumNotFoundException if the forum with the specified ID is not found
     */
    public void execute(UUID id) {

        ForumEntity forumDB = forumRepository.findById(id)
                .orElseThrow(ForumNotFoundException::new);

        forumRepository.delete(forumDB);
    }
}
