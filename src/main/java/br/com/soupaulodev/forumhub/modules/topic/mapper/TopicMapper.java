package br.com.soupaulodev.forumhub.modules.topic.mapper;

import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.CreateTopicRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;

public class TopicMapper {

    public static <T> TopicEntity toEntity(T t) {
        UserEntity user = null;
        ForumEntity forum = null;

        if (t instanceof CreateTopicRequestDTO) {
            user = ((CreateTopicRequestDTO) t).getAuthor();
            forum = ((CreateTopicRequestDTO) t).getForum();
        }

        return new TopicEntity(
            ((CreateTopicRequestDTO) t).getTitle(),
            ((CreateTopicRequestDTO) t).getMessage(),
            user,
            forum
        );
    }

    public static TopicResponseDTO toResponseDTO(TopicEntity topic) {
        return new TopicResponseDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getAuthor().getId(),
                topic.getAuthor().getName(),
                topic.getCreatedAt()
        );
    }
}
