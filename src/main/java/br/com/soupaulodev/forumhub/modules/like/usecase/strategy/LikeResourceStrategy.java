package br.com.soupaulodev.forumhub.modules.like.usecase.strategy;

import br.com.soupaulodev.forumhub.modules.like.entity.ResourceType;

import java.util.UUID;

/**
 * Interface for handling with the resource like strategy.
 *
 * @author <a href="https://soupaulodev.com.br>soupaulodev</a>
 */
public interface LikeResourceStrategy {

    /**
     * Likes a resource.
     *
     * @param resourceId the unique identifier of the resource to be liked
     */
    void likeResource(UUID resourceId);

    /**
     * Dislikes a resource.
     *
     * @param resourceId the unique identifier of the resource to be disliked
     */
    void dislikeResource(UUID resourceId);

    /**
     * Gets the resource type.
     *
     * @return the resource type
     */
    ResourceType getResourceType();
}
