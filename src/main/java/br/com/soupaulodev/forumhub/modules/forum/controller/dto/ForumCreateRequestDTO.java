package br.com.soupaulodev.forumhub.modules.forum.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

/**
 * Data Transfer Object for creating a forum.
 */
@Data
@AllArgsConstructor
public class ForumCreateRequestDTO {

    /**
     * The name of the forum.
     * Must not be blank and must be between 3 and 50 characters.
     */
    @NotBlank
    @Length(min = 3, max = 50)
    private String name;

    /**
     * The description of the forum.
     * Must not be blank and must be between 3 and 50 characters.
     */
    @NotBlank
    @Length(min = 3, max = 50)
    private String description;

    /**
     * The unique identifier of the forum owner.
     * Must not be blank.
     */
    @NotBlank
    private UUID ownerId;
}
