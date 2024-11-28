package br.com.soupaulodev.forumhub.modules.forum.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ForumCreateRequestDTO {
    @NotBlank
    @Length(min = 3, max = 50)
    private String name;

    @NotBlank
    @Length(min = 3, max = 50)
    private String description;

    @NotBlank
    private UUID ownerId;
}
