package br.com.soupaulodev.forumhub.modules.comment.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class CommentRequestDTO {
    @NotBlank
    @Length(min = 1, max = 255)
    private String content;
}
