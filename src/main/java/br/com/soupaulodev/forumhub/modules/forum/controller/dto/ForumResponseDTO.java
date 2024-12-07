package br.com.soupaulodev.forumhub.modules.forum.controller.dto;

import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) representing the response body for forum retrieval.
 * <p>
 *     This class is used to capture the forum's information, including its unique identifier, name, description, owner,
 *     participants, topic count, creation date, and last update date, when retrieving a forum. It is used to provide
 *     a response to the client with the forum's information.
 * </p>
 *
 * @param id the unique identifier of the forum
 * @param name the name of the forum
 * @param description the description of the forum
 * @param owner the owner of the forum
 * @param participants the participants of the forum
 * @param topicCount the number of topics in the forum
 * @param createdAt the creation date of the forum
 * @param updatedAt the last update date of the forum
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public record ForumResponseDTO (UUID id,
                                String name,
                                String description,
                                UserResponseDTO owner,
                                Set<UserResponseDTO> participants,
                                int topicCount,
                                Instant createdAt,
                                Instant updatedAt) {}