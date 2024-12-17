package br.com.soupaulodev.forumhub.modules.like.usecase.strategy.strategies;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.repository.ForumRepository;
import br.com.soupaulodev.forumhub.modules.like.entity.ResourceType;
import br.com.soupaulodev.forumhub.modules.like.usecase.strategy.LikeResourceStrategy;

import java.util.UUID;

/**
 * Like strategy for a forum.
 * This class provides the business logic for liking a forum.
 *
 * @author <a href="https://soupaulodev.com.br>soupaulodev</a>
 */
public class ForumLikeStrategy implements LikeResourceStrategy {

    private final ForumRepository forumRepository;

    /**
     * Constructor.
     *
     * @param forumRepository The forum repository
     */
    public ForumLikeStrategy(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    /**
     * Likes a forum.
     *
     * @param resourceId The forum id
     */
    @Override
    public void likeResource(UUID resourceId) {
        ForumEntity forum = forumRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("Forum does not exist"));
        forum.incrementHighs();
        forumRepository.save(forum);
    }

    /**
     * Dislikes a forum.
     *
     * @param resourceId The forum id
     */
    @Override
    public void dislikeResource(UUID resourceId) {
        ForumEntity forum = forumRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("Forum does not exist"));
        forum.decrementHighs();
        forumRepository.save(forum);
    }

    /**
     * Gets the resource type.
     *
     * @return the resource type
     */
    @Override
    public ResourceType getResourceType() {
        return ResourceType.FORUM;
    }
}
