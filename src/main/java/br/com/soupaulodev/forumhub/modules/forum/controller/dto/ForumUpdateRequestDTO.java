package br.com.soupaulodev.forumhub.modules.forum.controller.dto;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UUID;

/**
 * DTO (Data Transfer Object) representing the request body for forum update.
 * <p>
 *     This class is used to capture the forum's information, including its name, description, and owner ID,
 *     when updating an existing forum. It uses Jakarta Bean Validation annotations to enforce that the input is validated
 *     before processing, ensuring that the forum is updated correctly. It is used to provide
 *     a request to the client with the forum's information to be updated.
 * </p>
 *
 * @param name the name of the forum
 * @param description the description of the forum
 * @param ownerId the unique identifier of the forum owner
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public record ForumUpdateRequestDTO (@Size(max = 50)
                                     String name,

                                     @Size(max = 50)
                                     String description,

                                     @UUID String ownerId) {}