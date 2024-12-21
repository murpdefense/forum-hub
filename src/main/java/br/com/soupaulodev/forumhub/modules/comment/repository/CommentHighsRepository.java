package br.com.soupaulodev.forumhub.modules.comment.repository;

import br.com.soupaulodev.forumhub.modules.comment.entity.CommentHighsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for {@link CommentHighsEntity}.
 * Extends {@link JpaRepository} to provide CRUD operations for {@link CommentHighsEntity}.
 *
 * @author <a href="http://soupaulodev.com.br">soupaulodev</a>
 */
@Repository
public interface CommentHighsRepository extends JpaRepository<CommentHighsEntity, String> {
    Optional<CommentHighsEntity> findByComment_IdAndUser_Id(UUID commentId, UUID userId);
}
