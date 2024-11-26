package br.com.soupaulodev.forumhub.modules.comment.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CommentResponseDTO {

    private UUID id;
    private String content;
    private CommentResponseDTO parentComment;
}
