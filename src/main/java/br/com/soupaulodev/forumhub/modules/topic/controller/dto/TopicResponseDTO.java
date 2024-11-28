package br.com.soupaulodev.forumhub.modules.topic.controller.dto;

import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicState;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

/**
 * Data Transfer Object for representing a topic response.
 */
@Data
@AllArgsConstructor
public class TopicResponseDTO {
    private UUID id;
    private String title;
    private String content;
    private TopicState state;
    private ForumResponseDTO forum;
    private UserResponseDTO creator;
    private int commentCount;
    private int likeCount;
    private Instant createdAt;
    private Instant updatedAt;
}
