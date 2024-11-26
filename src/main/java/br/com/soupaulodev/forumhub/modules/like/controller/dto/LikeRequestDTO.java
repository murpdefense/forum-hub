package br.com.soupaulodev.forumhub.modules.like.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class LikeRequestDTO {

    private UUID resourceId;
    private UUID userId;
}
