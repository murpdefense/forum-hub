package br.com.soupaulodev.forumhub.modules.forum.controller.dto;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UUID;

/**
 * Data Transfer Object for updating a forum.
 */
public class ForumUpdateRequestDTO {

    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String description;

    private UUID ownerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }
}
