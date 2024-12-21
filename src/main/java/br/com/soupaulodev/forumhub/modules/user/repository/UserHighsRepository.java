package br.com.soupaulodev.forumhub.modules.user.repository;

import br.com.soupaulodev.forumhub.modules.user.entity.UserHighsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserHighsRepository extends JpaRepository<UserHighsEntity, String> {

    Optional<UserHighsEntity> findByHighedUser_IdAndHighingUser_Id(UUID highedUserId, UUID highingUserId);
}
