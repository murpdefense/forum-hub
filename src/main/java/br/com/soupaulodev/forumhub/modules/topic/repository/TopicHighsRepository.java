package br.com.soupaulodev.forumhub.modules.topic.repository;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicHighsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for {@link TopicHighsEntity}.
 * Extends {@link JpaRepository} to provide CRUD operations for {@link TopicHighsEntity}.
 *
 * @author <a href="http://soupaulodev.com.br">soupaulodev</a>
 */
@Repository
public interface TopicHighsRepository extends JpaRepository<TopicHighsEntity, String> {
    Optional<TopicHighsEntity> findByTopic_IdAndUser_Id(UUID topicId, UUID userId);
}
