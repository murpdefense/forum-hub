package br.com.soupaulodev.forumhub.modules.forum.controller.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.UUID;

@Data
@AllArgsConstructor
public class ForumUpdateRequestDTO {

    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String description;

    private UUID ownerId;
}
