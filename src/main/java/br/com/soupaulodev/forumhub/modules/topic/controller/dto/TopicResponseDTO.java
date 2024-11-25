package br.com.soupaulodev.forumhub.modules.topic.controller.dto;

import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TopicResponseDTO {
    UUID id;
    String title;
    String message;
    UUID authorId;
    String authorName;
    Instant CreatedAt;
}
