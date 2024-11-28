package br.com.soupaulodev.forumhub.modules.comment.controller.dto;

import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * Data Transfer Object for comment responses.
 */
@Data
@AllArgsConstructor
public class CommentResponseDTO {

    private UUID id;
    private String content;
    private UserResponseDTO user;
    private TopicResponseDTO topic;
    private UUID parentCommentId;
    private Set<CommentResponseDTO> replies;
    private Instant createdAt;
    private Instant updatedAt;
}
