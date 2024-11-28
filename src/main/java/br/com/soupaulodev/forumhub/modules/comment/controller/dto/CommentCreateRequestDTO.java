package br.com.soupaulodev.forumhub.modules.comment.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CommentCreateRequestDTO {
    @NotBlank
    @Size(max = 500)
    private String content;

    @NotNull
    private UUID userId;

    @NotNull
    private UUID topicId;

    private UUID parentCommentId;
}
