package br.com.soupaulodev.forumhub.modules.topic.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UUID;

/**
 * DTO (Data Transfer Object) representing the request body for topic creation.
 * <p>
 * This class is used to capture the topic's information, including its title, content, forum ID and creator ID,
 * when creating a new topic. It uses Jakarta Bean Validation annotations to enforce that the input is validated
 * before processing, ensuring that the topic is created correctly.
 * </p>
 *
 * @param title     the title of the topic
 * @param content   the content of the topic
 * @param forumId   the unique identifier of the forum to which the topic belongs
 * @param creatorId the unique identifier of the creator of the topic
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public record TopicCreateRequestDTO(
        @NotBlank
        @Size(max = 50)
        String title,

        @NotBlank
        @Size(max = 500)
        String content,

        @UUID String forumId,
        @UUID String creatorId
) {
}
