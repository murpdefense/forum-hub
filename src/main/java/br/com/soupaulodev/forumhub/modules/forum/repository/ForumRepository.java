package br.com.soupaulodev.forumhub.modules.forum.repository;

import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing ForumEntity instances.
 */
@Repository
public interface ForumRepository extends JpaRepository<ForumEntity, UUID> {

    /**
     * Finds a forum by its name.
     *
     * @param name the name of the forum
     * @return an Optional containing the found ForumEntity, or empty if not found
     */
    Optional<ForumEntity> findByName(String name);

    /**
     * Finds all forums with pagination support.
     *
     * @param pageable the pagination information
     * @return a list of ForumEntity instances
     */
    List<ForumEntity> findAllBy(Pageable pageable);

    /**
     * Finds all forums whose names contain the specified string, ordered by creation date in descending order.
     *
     * @param name the string to search for in forum names
     * @param pageable the pagination information
     * @return a list of ForumEntity instances
     */
    List<ForumEntity> findAllByNameContainingOrderByCreatedAtDesc(String name, Pageable pageable);
}
