package br.com.soupaulodev.forumhub.modules.comment.controller.dto;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) representing the response body for comment data.
 * <p>
 *     This class is used to capture the comment's information, including its content, user, topic, parent comment ID,
 *     replies, and creation and update timestamps. It is used to return the comment data in API responses.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public record CommentResponseDTO (
    UUID id,
    String content,
    UUID user,
    UUID topic,
    Long highs,
    UUID parentCommentId,
    List<CommentResponseDTO> replies,
    Instant createdAt,
    Instant updatedAt
) {}
