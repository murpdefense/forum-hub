package br.com.soupaulodev.forumhub.modules.comment.controller.dto;

import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * Data Transfer Object for comment responses.
 */
public class CommentResponseDTO {

    private UUID id;
    private String content;
    private UserResponseDTO user;
    private TopicResponseDTO topic;
    private UUID parentCommentId;
    private Set<CommentResponseDTO> replies;
    private Instant createdAt;
    private Instant updatedAt;

    public CommentResponseDTO(UUID id, String content, UserResponseDTO user, TopicResponseDTO topic, UUID parentCommentId, Set<CommentResponseDTO> replies, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.topic = topic;
        this.parentCommentId = parentCommentId;
        this.replies = replies;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public TopicResponseDTO getTopic() {
        return topic;
    }

    public void setTopic(TopicResponseDTO topic) {
        this.topic = topic;
    }

    public UUID getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(UUID parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public Set<CommentResponseDTO> getReplies() {
        return replies;
    }

    public void setReplies(Set<CommentResponseDTO> replies) {
        this.replies = replies;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
