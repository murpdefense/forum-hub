package br.com.soupaulodev.forumhub.modules.topic.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * Data Transfer Object for creating a new topic.
 */
@Data
@AllArgsConstructor
public class TopicCreateRequestDTO {

    /**
     * The title of the topic.
     * Must not be blank and must not exceed 50 characters.
     */
    @NotBlank
    @Size(max = 50)
    private String title;

    /**
     * The content of the topic.
     * Must not be blank and must not exceed 500 characters.
     */
    @NotBlank
    @Size(max = 500)
    private String content;

    /**
     * The unique identifier of the creator of the topic.
     * Must not be null.
     */
    @NotNull
    private UUID forumId;

    /**
     * The unique identifier of the creator of the topic.
     * Must not be null.
     */
    @NotNull
    private UUID creatorId;
}
