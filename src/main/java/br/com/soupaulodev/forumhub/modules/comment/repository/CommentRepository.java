package br.com.soupaulodev.forumhub.modules.comment.repository;

import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {
}
