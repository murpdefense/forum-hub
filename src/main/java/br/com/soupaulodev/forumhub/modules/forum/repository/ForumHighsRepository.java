package br.com.soupaulodev.forumhub.modules.forum.repository;

import br.com.soupaulodev.forumhub.modules.forum.entity.ForumHighsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for {@link ForumHighsEntity}.
 * Extends {@link JpaRepository} to provide CRUD operations for {@link ForumHighsEntity}.
 *
 * @author <a href="http://soupaulodev.com.br">soupaulodev</a>
 */
@Repository
public interface ForumHighsRepository extends JpaRepository<ForumHighsEntity, String> {
    Optional<ForumHighsEntity> findByForum_IdAndUser_Id(UUID forumId, UUID userId);
}
