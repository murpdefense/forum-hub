package br.com.soupaulodev.forumhub.modules.like.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.LikeNotFoundException;
import br.com.soupaulodev.forumhub.modules.like.entity.LikeEntity;
import br.com.soupaulodev.forumhub.modules.like.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for managing the unliking of resources.
 */
@Service
public class UnlikeResourceUsecase {

    private final LikeRepository likeRepository;

    /**
     * Constructs a new UnlikeResourceUsecase with the specified repository.
     *
     * @param likeRepository the repository for managing likes
     */
    public UnlikeResourceUsecase(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    /**
     * Executes the use case for unliking a topic.
     *
     * @param likeId the unique identifier of the like to be removed
     * @throws LikeNotFoundException if the like is not found
     */
    public void execute(UUID likeId) {

        LikeEntity likeFound = likeRepository.findById(likeId)
                .orElseThrow(LikeNotFoundException::new);

        likeRepository.delete(likeFound);
    }
}
