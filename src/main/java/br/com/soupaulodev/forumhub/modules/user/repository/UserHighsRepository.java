package br.com.soupaulodev.forumhub.modules.user.repository;

import br.com.soupaulodev.forumhub.modules.user.entity.UserHighsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for managing {@link UserHighsEntity} persistence.
 * Extends {@link JpaRepository} to provide basic CRUD operations.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Repository
public interface UserHighsRepository extends JpaRepository<UserHighsEntity, String> {

    /**
     * Finds a {@link UserHighsEntity} by the IDs of the highed user and the highing user.
     *
     * @param highedUserId the ID of the user who is the recipient of the high
     * @param highingUserId the ID of the user giving the high
     * @return an {@link Optional} containing the {@link UserHighsEntity} if found, or empty if no match is found
     */
    Optional<UserHighsEntity> findByHighedUser_IdAndHighingUser_Id(UUID highedUserId, UUID highingUserId);
}
