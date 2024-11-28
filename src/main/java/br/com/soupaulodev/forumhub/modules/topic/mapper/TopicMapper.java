package br.com.soupaulodev.forumhub.modules.topic.mapper;

import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.forum.mapper.ForumMapper;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;

/**
 * Mapper class for converting between TopicEntity and various DTOs.
 */
public class TopicMapper {

    /**
     * Converts a TopicCreateRequestDTO to a TopicEntity.
     *
     * @param dto the TopicCreateRequestDTO containing the topic creation data
     * @param forum the ForumEntity to which the topic belongs
     * @param creator the UserEntity who created the topic
     * @return the TopicEntity created from the DTO
     */
    public static TopicEntity toEntity(TopicCreateRequestDTO dto, ForumEntity forum, UserEntity creator) {
        return new TopicEntity(
                dto.getTitle(),
                dto.getContent(),
                creator,
                forum
        );
    }

    /**
     * Converts a TopicUpdateRequestDTO to a TopicEntity.
     *
     * @param dto the TopicUpdateRequestDTO containing the topic update data
     * @return the TopicEntity created from the DTO
     */
    public static TopicEntity toEntity(TopicUpdateRequestDTO dto) {
        return new TopicEntity(
                dto.getTitle(),
                dto.getContent(),
                dto.getState()
        );
    }

    /**
     * Converts a TopicEntity to a TopicResponseDTO.
     *
     * @param topic the TopicEntity to be converted
     * @return the TopicResponseDTO created from the entity
     */
    public static TopicResponseDTO toResponseDTO(TopicEntity topic) {

        UserResponseDTO creator = UserMapper.toResponseDTO(topic.getCreator());
        ForumResponseDTO forum = ForumMapper.toResponseDTO(topic.getForum());
        int commentCount = topic.getComments().size();
        int likeCount = topic.getLikes().size();

        return new TopicResponseDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getContent(),
                topic.getState(),
                forum,
                creator,
                commentCount,
                likeCount,
                topic.getCreatedAt(),
                topic.getUpdatedAt()
        );
    }
}
