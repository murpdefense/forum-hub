package br.com.soupaulodev.forumhub.modules.comment.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

/**
 * Data Transfer Object for creating a comment.
 */
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getTopicId() {
        return topicId;
    }

    public void setTopicId(UUID topicId) {
        this.topicId = topicId;
    }

    public UUID getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(UUID parentCommentId) {
        this.parentCommentId = parentCommentId;
    }
}