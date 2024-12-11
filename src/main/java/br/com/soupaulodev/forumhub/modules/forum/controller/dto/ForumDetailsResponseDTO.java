package br.com.soupaulodev.forumhub.modules.forum.controller.dto;

import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) representing the response body for forum retrieval.
 * <p>
 *     This class is used to capture the forum's information details when a forum is retrieved.
 *     The forum details include the forum's unique identifier, name, description, owner, participants, recent topics,
 *     creation date, and last update date.
 * </p>
 *
 * @param id the unique identifier of the forum
 * @param name the name of the forum
 * @param description the description of the forum
 * @param owner the owner of the forum
 * @param topicCount the number of topics in the forum
 * @param createdAt the creation date of the forum
 * @param updatedAt the last update date of the forum
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public record ForumDetailsResponseDTO(UUID id,
                                      String name,
                                      String description,
                                      UUID owner,
                                      List<TopicResponseDTO> topicCount,
                                      Instant createdAt,
                                      Instant updatedAt) {}