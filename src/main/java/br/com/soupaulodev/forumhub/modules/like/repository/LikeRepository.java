package br.com.soupaulodev.forumhub.modules.like.repository;

import br.com.soupaulodev.forumhub.modules.like.entity.LikeEntity;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing LikeEntity instances.
 */
public interface LikeRepository extends JpaRepository<LikeEntity, UUID> {

    /**
     * Finds a LikeEntity by the given user and topic.
     *
     * @param userId the user who liked the topic
     * @param topicId the topic that was liked
     * @return an Optional containing the found LikeEntity, or empty if not found
     */
    Optional<LikeEntity> findByUserAndTopic(UserEntity userId, TopicEntity topicId);
}
