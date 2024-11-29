package br.com.soupaulodev.forumhub.modules.user.controller.dto;

import br.com.soupaulodev.forumhub.modules.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

/**
 * Data Transfer Object for creating a new user.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDTO {

    private UUID id;
    private String name;
    private String username;
    private String email;
    private UserRole role;
    private Instant createdAt;
    private Instant updatedAt;
}
