package br.com.soupaulodev.forumhub.modules.forum.controller.dto;

import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * Data Transfer Object for creating a forum.
 */
public class ForumResponseDTO {

    private UUID id;
    private String name;
    private String description;
    private UserResponseDTO owner;
    private Set<UserResponseDTO> participants;
    private int topicCount;
    private Instant createdAt;
    private Instant updatedAt;

    public ForumResponseDTO(UUID id, String name, String description, UserResponseDTO owner, Set<UserResponseDTO> participants, int topicCount, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.participants = participants;
        this.topicCount = topicCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserResponseDTO getOwner() {
        return owner;
    }

    public void setOwner(UserResponseDTO owner) {
        this.owner = owner;
    }

    public Set<UserResponseDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<UserResponseDTO> participants) {
        this.participants = participants;
    }

    public int getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(int topicCount) {
        this.topicCount = topicCount;
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
