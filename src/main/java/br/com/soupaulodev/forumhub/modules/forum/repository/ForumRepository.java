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
     * Finds a forum by their name.
     *
     * @param name {@link String} the name of the forum
     * @return an {@link Optional} containing the found {@link ForumEntity}, or empty if not found
     */
    Optional<ForumEntity> findByName(String name);

    /**
     * Checks if a forum with the specified name exists.
     *
     * @param name {@link String} the name of the forum
     * @return a {@link Boolean} indicating whether a forum with the specified name exists
     */
    Boolean existsByName(String name);

    /**
     * Finds all forums with pagination support.
     *
     * @param pageable {@link Pageable} the pagination information
     * @return a {@link List} of {@link ForumEntity} instances
     */
    List<ForumEntity> findAllBy(Pageable pageable);

    /**
     * Finds all forums whose names contain the specified string, ordered by creation date in descending order.
     *
     * @param name {@link String} the string to search for in forum names
     * @param pageable {@link Pageable} the pagination information
     * @return a {@link List} of {@link ForumEntity} instances
     */
    List<ForumEntity> findAllByNameContainingOrderByCreatedAtDesc(String name, Pageable pageable);
}
