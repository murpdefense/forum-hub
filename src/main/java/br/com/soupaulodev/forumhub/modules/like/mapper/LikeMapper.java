package br.com.soupaulodev.forumhub.modules.like.mapper;

import br.com.soupaulodev.forumhub.modules.like.controller.dto.LikeResponseDTO;
import br.com.soupaulodev.forumhub.modules.like.entity.LikeEntity;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.mapper.TopicMapper;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;

public class LikeMapper {

    public static LikeEntity toEntity(UserEntity user, TopicEntity topic) {
        return new LikeEntity(user, topic);
    }

    public static LikeResponseDTO toResponseDTO(LikeEntity likeEntity) {
        return new LikeResponseDTO(
                likeEntity.getId(),
                UserMapper.toResponseDTO(likeEntity.getUser()),
                TopicMapper.toResponseDTO(likeEntity.getTopic()),
                likeEntity.getCreatedAt()
        );
    }
}
