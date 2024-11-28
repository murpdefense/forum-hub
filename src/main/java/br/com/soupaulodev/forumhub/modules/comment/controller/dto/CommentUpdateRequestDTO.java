package br.com.soupaulodev.forumhub.modules.comment.controller.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentUpdateRequestDTO {
    @Size(max = 500)
    private String content;
}
