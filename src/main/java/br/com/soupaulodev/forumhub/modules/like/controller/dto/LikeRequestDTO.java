package br.com.soupaulodev.forumhub.modules.like.controller.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Data Transfer Object for like requests.
 */
public class LikeRequestDTO {

    /**
     * The unique identifier of the user.
     */
    @NotNull
    private UUID userId;

    /**
     * The unique identifier of the topic.
     */
    @NotNull
    private UUID topicId;

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
}
