package br.com.soupaulodev.forumhub.modules.forum.controller.dto;

import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserRequestDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class ForumRequestDTO {
    @NotBlank
    @Length(min = 3, max = 50)
    private String name;

    @NotBlank
    @Length(min = 3, max = 50)
    private String description;

    @NotBlank
    private UserRequestDTO owner;
}
