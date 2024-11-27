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

public class TopicMapper {

    public static TopicEntity toEntity(TopicCreateRequestDTO dto, ForumEntity forum, UserEntity creator) {
        return new TopicEntity(
                dto.getTitle(),
                dto.getContent(),
                creator,
                forum
        );
    }

    public static TopicEntity toEntity(TopicUpdateRequestDTO dto) {
        return new TopicEntity(
                dto.getTitle(),
                dto.getContent(),
                dto.getState()
        );
    }

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
