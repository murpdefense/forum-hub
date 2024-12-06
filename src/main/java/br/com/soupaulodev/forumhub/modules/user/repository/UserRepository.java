package br.com.soupaulodev.forumhub.modules.user.repository;

import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for UserEntity.
 * Extends {@link JpaRepository} to provide CRUD operations for {@link UserEntity}.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    /**
     * Finds a user by their username.
     *
     * @param username {@link String} the username of the user
     * @return an {@link Optional} containing the found {@link UserEntity}, or empty if no user is found
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Finds a user by their username and email.
     *
     * @param username {@link String} the username of the user
     * @param email {@link String} the email of the user
     * @return an {@link Optional} containing the found {@link UserEntity}, or empty if no user is found
     */
    Optional<UserEntity> findByUsernameAndEmail(String username, String email);

    /**
     * Finds a user by their name or username.
     *
     * @param name {@link String} the name of the user
     * @param username {@link String} the username of the user
     * @return an {@link Optional} containing the found {@link UserEntity}, or empty if no user is found
     */
    Optional<UserEntity> findByNameOrUsername(String name, String username);

    /**
     * Checks if a user with the given username exists.
     *
     * @param username {@link String}the username to check
     * @return true {@link Boolean} if a user with the given username exists, false otherwise
     */
    Boolean existsByUsername(String username);
}
