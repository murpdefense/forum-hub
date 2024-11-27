package br.com.soupaulodev.forumhub.modules.topic.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TopicCreateRequestDTO {

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    @Size(max = 500)
    private String content;

    @NotNull
    private UUID forumId;

    @NotNull
    private UUID creatorId;
}
