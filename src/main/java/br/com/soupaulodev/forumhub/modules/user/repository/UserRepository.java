package br.com.soupaulodev.forumhub.modules.user.repository;

import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for UserEntity.
 * Extends JpaRepository to provide CRUD operations for UserEntity.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    /**
     * Finds a user by their username and email.
     *
     * @param username the username of the user
     * @param email the email of the user
     * @return an Optional containing the found UserEntity, or empty if no user is found
     */
    Optional<UserEntity> findByUsernameAndEmail(String username, String email);

    /**
     * Finds a user by their name or username.
     *
     * @param name the name of the user
     * @param username the username of the user
     * @return an Optional containing the found UserEntity, or empty if no user is found
     */
    Optional<UserEntity> findByNameOrUsername(String name, String username);
}
