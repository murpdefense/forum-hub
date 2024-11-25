package br.com.soupaulodev.forumhub.modules.forum.repository;

import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ForumRepository extends JpaRepository<ForumEntity, UUID> {
    Optional<ForumEntity> findByName(String name);
    List<ForumEntity> findAllOrderByCreatedAt(Pageable pageable);
    List<ForumEntity> findAllByNameContainingOrderByCreatedAtDesc(String name, Pageable pageable);
}
