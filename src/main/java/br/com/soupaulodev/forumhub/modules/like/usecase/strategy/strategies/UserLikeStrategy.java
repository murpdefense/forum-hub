package br.com.soupaulodev.forumhub.modules.like.usecase.strategy.strategies;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.like.entity.ResourceType;
import br.com.soupaulodev.forumhub.modules.like.usecase.strategy.LikeResourceStrategy;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;

import java.util.UUID;

/**
 * Like strategy for a user.
 * This class provides the business logic for liking a user.
 *
 * @author <a href="https://soupaulodev.com.br>soupaulodev</a>
 */
public class UserLikeStrategy implements LikeResourceStrategy {

    private final UserRepository userRepository;

    /**
     * Constructor.
     *
     * @param userRepository the user repository
     */
    public UserLikeStrategy(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Likes a user.
     *
     * @param resourceId the user id
     */
    @Override
    public void likeResource(UUID resourceId) {
        UserEntity user = userRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        user.incrementHighs();
        userRepository.save(user);
    }

    /**
     * Dislikes a user.
     *
     * @param resourceId The user id
     */
    @Override
    public void dislikeResource(UUID resourceId) {
        UserEntity user = userRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
        user.decrementHighs();
        userRepository.save(user);
    }

    /**
     * Gets the resource type.
     *
     * @return the resource type
     */
    @Override
    public ResourceType getResourceType() {
        return ResourceType.USER;
    }
}
