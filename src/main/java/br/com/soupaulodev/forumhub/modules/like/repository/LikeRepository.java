package br.com.soupaulodev.forumhub.modules.like.repository;

import br.com.soupaulodev.forumhub.modules.like.entity.LikeEntity;
import br.com.soupaulodev.forumhub.modules.like.entity.ResourceType;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing LikeEntity instances.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public interface LikeRepository extends JpaRepository<LikeEntity, UUID> {

    /**
     * Find all likes by resource type and resource id.
     *
     * @param resourceType the resource type
     * @param resourceId the resource id
     * @return List of LikeEntity
     */
    List<LikeEntity> findByResourceTypeAndResourceId(ResourceType resourceType, UUID resourceId);

    /**
     * Find all likes by resource type and resource id and user.
     *
     * @param resourceType the resource type
     * @param resourceId the resource id
     * @param user the user
     * @return Optional of LikeEntity
     */
    Optional<LikeEntity> findByResourceTypeAndResourceIdAndUser(ResourceType resourceType, UUID resourceId, UserEntity user);

    /**
     * Count all likes by resource type and resource id.
     *
     * @param resourceType the resource type
     * @param resourceId the resource id
     * @return Long of count
     */
    Long countByResourceTypeAndResourceId(ResourceType resourceType, UUID resourceId);
    }
