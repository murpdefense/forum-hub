package br.com.soupaulodev.forumhub.modules.forum.controller.dto;

import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * Data Transfer Object for creating a forum.
 */
@Data
@AllArgsConstructor
public class ForumResponseDTO {

    private UUID id;
    private String name;
    private String description;
    private UserResponseDTO owner;
    private Set<UserResponseDTO> participants;
    private int topicCount;
    private Instant createdAt;
    private Instant updatedAt;
}
