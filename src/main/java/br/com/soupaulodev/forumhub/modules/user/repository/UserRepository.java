package br.com.soupaulodev.forumhub.modules.user.repository;

import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for managing {@link UserEntity} persistence.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    /**
     * Finds a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the {@link UserEntity} if found, or empty if not
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Checks if a user exists by their username.
     *
     * @param username the username to check
     * @return true if a user with the given username exists, otherwise false
     */
    boolean existsByUsername(String username);
}
