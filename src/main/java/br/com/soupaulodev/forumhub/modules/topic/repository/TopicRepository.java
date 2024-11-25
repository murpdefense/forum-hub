package br.com.soupaulodev.forumhub.modules.topic.repository;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, UUID> {
    List<TopicEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<TopicEntity> findAllByTitleContainingIgnoreCaseOrMessageContainingIgnoreCaseOrderByCreatedAtDesc(String title, String message, Pageable pageable);
}
