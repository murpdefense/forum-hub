package br.com.soupaulodev.forumhub.modules.user.controller.dto;

import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;

import java.time.Instant;
import java.util.UUID;

/**
 * Data transfer object for the forum in which the user participates.
 *
 * @param id
 * @param name
 * @param createdAt
 * @author <a href="https://soupaulodev.com.br">soupauldev</a>
 */
public record ParticipatesInDTO(UUID id,
                                String name,
                                Instant createdAt) {
    public static ParticipatesInDTO from(ForumEntity forum) {
        return new ParticipatesInDTO(
                forum.getId(),
                forum.getName(),
                forum.getCreatedAt()
        );
    }
}