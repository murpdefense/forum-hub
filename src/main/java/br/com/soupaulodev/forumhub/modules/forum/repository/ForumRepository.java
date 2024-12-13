package br.com.soupaulodev.forumhub.modules.forum.repository;

import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for {@link ForumEntity}.
 * Extends {@link JpaRepository} to provide CRUD operations for {@link ForumEntity}.
 */
@Repository
public interface ForumRepository extends JpaRepository<ForumEntity, UUID> {

    /**
     * Checks if a forum with the specified name exists.
     *
     * @param name {@link String} the name of the forum
     * @return a {@link Boolean} indicating whether a forum with the specified name exists
     */
    Boolean existsByName(String name);
}
