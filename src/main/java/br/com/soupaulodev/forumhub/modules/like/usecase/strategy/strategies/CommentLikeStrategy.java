package br.com.soupaulodev.forumhub.modules.like.usecase.strategy.strategies;

import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.comment.repository.CommentRepository;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.like.entity.ResourceType;
import br.com.soupaulodev.forumhub.modules.like.usecase.strategy.LikeResourceStrategy;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Like strategy for a comment.
 * This class provides the business logic for liking a comment.
 *
 * @author <a href="https://soupaulodev.com.br>soupaulodev</a>
 */
@Service
public class CommentLikeStrategy implements LikeResourceStrategy {

    private final CommentRepository commentRepository;

    /**
     * Constructor.
     *
     * @param commentRepository the comment repository
     */
    public CommentLikeStrategy(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Likes a comment.
     *
     * @param resourceId the comment id
     */
    @Override
    public void likeResource(UUID resourceId) {
        CommentEntity comment = commentRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment does not exist"));
        comment.incrementHighs();
        commentRepository.save(comment);
    }

    /**
     * Dislikes a comment.
     *
     * @param resourceId the comment id
     */
    @Override
    public void dislikeResource(UUID resourceId) {
        CommentEntity comment = commentRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment does not exist"));
        comment.decrementHighs();
        commentRepository.save(comment);
    }

    /**
     * Gets the resource type.
     *
     * @return the resource type
     */
    @Override
    public ResourceType getResourceType() {
        return ResourceType.COMMENT;
    }
}
