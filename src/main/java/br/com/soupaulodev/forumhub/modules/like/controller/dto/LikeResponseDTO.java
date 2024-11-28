package br.com.soupaulodev.forumhub.modules.like.controller.dto;

import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class LikeResponseDTO {
    private UUID id;
    private UserResponseDTO user;
    private TopicResponseDTO topic;
    private Instant createdAt;
}
