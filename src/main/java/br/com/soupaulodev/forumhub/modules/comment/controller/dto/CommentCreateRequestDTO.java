package br.com.soupaulodev.forumhub.modules.comment.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

/**
 * Data Transfer Object for creating a comment.
 */
@Data
@AllArgsConstructor
public class CommentCreateRequestDTO {

    /**
     * The content of the comment.
     * Must not be blank and must not exceed 500 characters.
     */
    @NotBlank
    @Size(max = 500)
    private String content;

    /**
     * The UUID of the user creating the comment.
     * Must not be null.
     */
    @NotNull
    private UUID userId;

    /**
     * The UUID of the topic to which the comment belongs.
     * Must not be null.
     */
    @NotNull
    private UUID topicId;

    /**
     * The UUID of the parent comment, if this comment is a reply.
     * Can be null.
     */
    private UUID parentCommentId;
}