package br.com.soupaulodev.forumhub.modules.like.usecase.strategy.strategies;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.like.entity.ResourceType;
import br.com.soupaulodev.forumhub.modules.like.usecase.strategy.LikeResourceStrategy;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Like strategy for a topic.
 * This class provides the business logic for liking a topic.
 *
 * @author <a href="https://soupaulodev.com.br>soupaulodev</a>
 */
@Service
public class TopicLikeStrategy implements LikeResourceStrategy {

    private final TopicRepository topicRepository;

    /**
     * Constructor.
     *
     * @param topicRepository the topic repository
     */
    public TopicLikeStrategy(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * Likes a topic.
     *
     * @param resourceId the topic id
     */
    @Override
    public void likeResource(UUID resourceId) {
        TopicEntity topic = topicRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic does not exist"));
        topic.incrementHighs();
        topicRepository.save(topic);
    }

    /**
     * Dislikes a topic.
     *
     * @param resourceId The topic id
     */
    @Override
    public void dislikeResource(UUID resourceId) {
        TopicEntity topic = topicRepository.findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic does not exist"));
        topic.decrementHighs();
        topicRepository.save(topic);
    }

    /**
     * Gets the resource type.
     *
     * @return the resource type
     */
    @Override
    public ResourceType getResourceType() {
        return ResourceType.TOPIC;
    }
}
