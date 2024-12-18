package br.com.soupaulodev.forumhub.modules.topic.controller.dto;

import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) representing the request body for topic update.
 * <p>
 * This class is used to capture the topic's information, including its title, content, forum ID and creator ID,
 * when updating an existing forum. It uses Jakarta Bean Validation annotations to enforce that the input is validated
 * before processing, ensuring that the topic is updated correctly. It is used to provide
 * a request to the client with the topic's information to be updated.
 * </p>
 *
 * @param title   the name of the forum
 * @param content the description of the forum
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public record TopicUpdateRequestDTO(
        @Size(max = 50) String title,
        @Size(max = 500) String content
) {
}
