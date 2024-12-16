package br.com.soupaulodev.forumhub.modules.comment.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) representing the request body for comment update.
 * <p>
 *     This class is used to capture the comment's information, including its content, when updating an existing comment.
 * </p>
 * @param content
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public record CommentUpdateRequestDTO(
    @NotBlank
    @Size(max = 500)
    String content
) {}