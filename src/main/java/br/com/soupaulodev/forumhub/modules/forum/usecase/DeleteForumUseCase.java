package br.com.soupaulodev.forumhub.modules.forum.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForbiddenException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case for deleting a forum.
 */
@Service
public class DeleteForumUseCase {

    private final ForumRepository forumRepository;

    /**
     * Constructs a new DeleteForumUsecase with the specified repository.
     *
     * @param forumRepository the repository for managing forums
     */
    public DeleteForumUseCase(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    /**
     * Executes the use case to delete a forum by its unique identifier.
     *
     * @param id the unique identifier of the forum to be deleted
     * @param authenticatedUserId the unique identifier of the authenticated user
     * @throws ResourceNotFoundException if the forum with the specified ID is not found
     * @throws ForbiddenException if the authenticated user is not the owner of the forum
     */
    public void execute(UUID id, UUID authenticatedUserId) {
        ForumEntity forumDB = forumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Forum not found."));

        if (!forumDB.getOwner().getId().equals(authenticatedUserId)) {
            throw new ForbiddenException("You are not allowed to delete this forum.");
        }

        forumRepository.delete(forumDB);
    }
}
