package br.com.soupaulodev.forumhub.modules.user.controller.dto;

import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;

import java.time.Instant;
import java.util.UUID;

public record ParticipantesInDTO (UUID id,
                      String name,
                      Instant createdAt) {
    public static ParticipantesInDTO from(ForumEntity forum) {
        return new ParticipantesInDTO(
                forum.getId(),
                forum.getName(),
                forum.getCreatedAt()
        );
    }
}