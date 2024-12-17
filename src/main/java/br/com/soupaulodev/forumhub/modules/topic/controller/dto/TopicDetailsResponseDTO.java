package br.com.soupaulodev.forumhub.modules.topic.controller.dto;

import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentResponseDTO;
import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) representing the response body for topic details retrieval.
 * <p>
 *     This class is used to capture the topic's information, including its unique identifier, name, description, owner,
 *     participants, topic count, creation date, and last update date, when retrieving a forum. It is used to provide
 *     a response to the client with the forum's information.
 * </p>
 *
 * @param id the unique identifier of the topic
 * @param title the title of the topic
 * @param content the content of the topic
 * @param forumId the forum to which the topic belongs
 * @param creatorId the user who created the topic
 * @param likeCount the number of likes the topic has
 * @param commentCount the number of comments the topic has
 * @param lastComment the last comment made on the topic
 * @param createdAt the creation date of the topic
 * @param updatedAt the last update date of the topic
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public record TopicDetailsResponseDTO(
        UUID id,
        String title,
        String content,
        UUID forumId,
        UUID creatorId,
        String creatorUsername,
        Long likeCount,
        Long commentCount,
        List<CommentResponseDTO> comments,
        Instant createdAt,
        Instant updatedAt
) {}
