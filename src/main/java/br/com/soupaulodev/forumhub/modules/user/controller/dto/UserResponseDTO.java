package br.com.soupaulodev.forumhub.modules.user.controller.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Data Transfer Object for creating a new user.
 */
public record UserResponseDTO (UUID id,
                               String name,
                               String username,
                               String email,
                               Instant createdAt,
                               Instant updatedAt) {}
