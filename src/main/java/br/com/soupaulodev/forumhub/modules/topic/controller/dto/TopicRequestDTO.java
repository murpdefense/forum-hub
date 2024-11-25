package br.com.soupaulodev.forumhub.modules.topic.controller.dto;

import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class TopicRequestDTO {

    @NotBlank
    @Length(min = 3, max = 50)
    private String title;

    @NotBlank
    @Length(min = 3, max = 500)
    private String message;

    @NotBlank
    private UserEntity author;

    @NotBlank
    private ForumEntity forum;
}
