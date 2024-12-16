package br.com.soupaulodev.forumhub.modules.user.controller.dto;

import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for the owner of a forum.
 * @param id
 * @param name
 * @param createdAt
 *
 * @author <a href="https://soupaulodev.com.br">soupauldev</a>
 */
public record OwnerOfDTO(UUID id,
                         String name,
                         Instant createdAt) {
    public static OwnerOfDTO from(ForumEntity forum) {
        return new OwnerOfDTO(
                forum.getOwner().getId(),
                forum.getOwner().getName(),
                forum.getOwner().getCreatedAt()
        );
    }
}