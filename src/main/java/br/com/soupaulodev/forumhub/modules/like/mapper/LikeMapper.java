package br.com.soupaulodev.forumhub.modules.like.mapper;

import br.com.soupaulodev.forumhub.modules.like.controller.dto.LikeRequestDTO;
import br.com.soupaulodev.forumhub.modules.like.entity.LikeEntity;

public class LikeMapper {

    public static LikeEntity toEntity(LikeRequestDTO likeRequestDTO) {
        return new LikeEntity(likeRequestDTO.getResourceId(), likeRequestDTO.getUserId());
    }
}
