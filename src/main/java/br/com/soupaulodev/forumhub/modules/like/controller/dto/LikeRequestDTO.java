package br.com.soupaulodev.forumhub.modules.like.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class LikeRequestDTO {

    @NotNull
    private UUID userId;

    @NotNull
    private UUID topicId;
}
