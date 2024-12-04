package br.com.soupaulodev.forumhub.modules.comment.controller.dto;

import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for updating a comment.
 */
public class CommentUpdateRequestDTO {
    @Size(max = 500)
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
