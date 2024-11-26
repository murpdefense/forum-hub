package br.com.soupaulodev.forumhub.modules.like.repository;

import br.com.soupaulodev.forumhub.modules.like.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LikeRepository extends JpaRepository<LikeEntity, UUID> {
}
