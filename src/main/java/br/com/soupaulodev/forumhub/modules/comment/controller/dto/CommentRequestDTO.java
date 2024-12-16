package br.com.soupaulodev.forumhub.modules.comment.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UUID;

/**
 * DTO (Data Transfer Object) representing the request body for comment creation.
 * <p>
 *     This class is used to capture the comment's information, including its content, user ID, topic ID, and parent comment ID,
 *     when creating a new comment. It uses Jakarta Bean Validation annotations to enforce that the input is validated
 *     before processing, ensuring that the comment is created correctly.
 * </p>
 * @param content
 * @param userId
 * @param topicId
 * @param parentCommentId
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public record CommentRequestDTO(
    @NotBlank
    @Size(max = 500)
    String content,
    @UUID
    String userId,
    @UUID
    String topicId,
    String parentCommentId
) {
    public CommentRequestDTO {
        if (parentCommentId != null && parentCommentId.isEmpty()) {
            parentCommentId = null;
        }
    }
}