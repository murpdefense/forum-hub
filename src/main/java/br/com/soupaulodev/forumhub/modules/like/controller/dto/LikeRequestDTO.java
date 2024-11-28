package br.com.soupaulodev.forumhub.modules.like.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * Data Transfer Object for like requests.
 */
@Data
@AllArgsConstructor
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
}
