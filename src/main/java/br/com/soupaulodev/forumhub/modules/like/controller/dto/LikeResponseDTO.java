package br.com.soupaulodev.forumhub.modules.like.controller.dto;

import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;

import java.time.Instant;
import java.util.UUID;

/**
 * Data Transfer Object for like requests.
 */
public class LikeResponseDTO {
    private UUID id;
    private UserResponseDTO user;
    private TopicResponseDTO topic;
    private Instant createdAt;

    public LikeResponseDTO(UUID id, UserResponseDTO user, TopicResponseDTO topic, Instant createdAt) {
        this.id = id;
        this.user = user;
        this.topic = topic;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
