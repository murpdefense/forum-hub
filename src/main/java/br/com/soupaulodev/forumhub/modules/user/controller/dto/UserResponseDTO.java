package br.com.soupaulodev.forumhub.modules.user.controller.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) representing the response body for user response.
 * <p>
 *     This class is used to capture the user's information, including their ID, name, username, email, and timestamps
 *     for when the user was created and last updated. It is used to return the user's information in the response body.
 * </p>
 *
 * @param id the ID of the user
 * @param name the name of the user
 * @param username the username of the user
 * @param email the email of the user
 * @param createdAt the timestamp for when the user was created
 * @param updatedAt the timestamp for when the user was last updated
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public record UserResponseDTO (UUID id,
                               String name,
                               String username,
                               String email,
                               Instant createdAt,
                               Instant updatedAt) {}
