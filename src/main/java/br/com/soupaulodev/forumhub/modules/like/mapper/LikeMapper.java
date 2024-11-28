package br.com.soupaulodev.forumhub.modules.like.mapper;

import br.com.soupaulodev.forumhub.modules.like.controller.dto.LikeResponseDTO;
import br.com.soupaulodev.forumhub.modules.like.entity.LikeEntity;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.mapper.TopicMapper;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;

/**
 * Mapper class for converting between LikeEntity and LikeResponseDTO.
 */
public class LikeMapper {

    /**
     * Converts the given user and topic to a LikeEntity.
     *
     * @param user the user who liked the topic
     * @param topic the topic that was liked
     * @return the created LikeEntity
     */
    public static LikeEntity toEntity(UserEntity user, TopicEntity topic) {
        return new LikeEntity(user, topic);
    }

    /**
     * Converts the given LikeEntity to a LikeResponseDTO.
     *
     * @param likeEntity the LikeEntity to convert
     * @return the created LikeResponseDTO
     */
    public static LikeResponseDTO toResponseDTO(LikeEntity likeEntity) {
        return new LikeResponseDTO(
                likeEntity.getId(),
                UserMapper.toResponseDTO(likeEntity.getUser()),
                TopicMapper.toResponseDTO(likeEntity.getTopic()),
                likeEntity.getCreatedAt()
        );
    }
}
