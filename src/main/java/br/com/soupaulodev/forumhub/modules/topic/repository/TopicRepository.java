package br.com.soupaulodev.forumhub.modules.topic.repository;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Mapper class for converting between TopicEntity and various DTOs.
 */
@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, UUID> {

    /**
     * Retrieves a list of topics ordered by creation date in descending order.
     *
     * @param pageable the pagination information
     * @return a list of TopicEntity instances
     */
    List<TopicEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);

    /**
     * Retrieves a list of topics where the title or content contains the specified keyword,
     * ordered by creation date in descending order.
     *
     * @param title the keyword to search in the title
     * @param message the keyword to search in the content
     * @param pageable the pagination information
     * @return a list of TopicEntity instances
     */
    List<TopicEntity> findAllByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByCreatedAtDesc(String title, String message, Pageable pageable);
}
