package br.com.soupaulodev.forumhub.modules.like.controller.dto;

import br.com.soupaulodev.forumhub.modules.like.entity.ResourceType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UUID;

/**
 * DTO representing a request to like a resource.
 * <p>
 * This class is used to capture the information needed to like a resource, including the type of the resource and
 * its unique identifier. It uses Jakarta Bean Validation annotations to enforce that the input is validated before
 * processing, ensuring that the like is created correctly.
 * </p>
 *
 * @param resourceType
 * @param resourceId
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public record LikeRequestDTO(
        @Enumerated(EnumType.STRING) @NotBlank ResourceType resourceType,
        @UUID @NotBlank String resourceId
) {
}