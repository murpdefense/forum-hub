package br.com.soupaulodev.forumhub.modules.topic.mapper;

import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicDetailsResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;

import java.util.List;

/**
 * Mapper class for converting between TopicEntity and various DTOs.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public class TopicMapper {

    /**
     * Converts a TopicCreateRequestDTO to a TopicEntity.
     *
     * @param dto     the TopicCreateRequestDTO containing the topic creation data
     * @param forum   the ForumEntity to which the topic belongs
     * @param creator the UserEntity who created the topic
     * @return the TopicEntity created from the DTO
     */
    public static TopicEntity toEntity(TopicCreateRequestDTO dto, ForumEntity forum, UserEntity creator) {
        return new TopicEntity(
                dto.title(),
                dto.content(),
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
                dto.title(),
                dto.content()
        );
    }

    /**
     * Converts a TopicEntity to a TopicResponseDTO.
     *
     * @param topic the TopicEntity to be converted
     * @return the TopicResponseDTO created from the entity
     */
    public static TopicResponseDTO toResponseDTO(TopicEntity topic) {
        return new TopicResponseDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getContent(),
                topic.getForum().getId(),
                topic.getCreator().getId(),
                topic.getCreator().getUsername(),
                topic.getHighsCount(),
                topic.getCommentsCount(),
                topic.getCreatedAt(),
                topic.getUpdatedAt()
        );
    }

    /**
     * Converts a TopicEntity to a TopicDetailsResponseDTO.
     *
     * @param topic the TopicEntity to be converted
     * @return the TopicDetailsResponseDTO created from the entity
     */
    public static TopicDetailsResponseDTO toDetailsResponseDTO(TopicEntity topic) {
        return new TopicDetailsResponseDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getContent(),
                topic.getForum().getId(),
                topic.getCreator().getId(),
                topic.getCreator().getUsername(),
                topic.getHighsCount(),
                topic.getCommentsCount(),
                List.of(), //TODO - Convert TopicMapper to Spring Component and inject CommentMapper
//                topic.getComments().stream().map(CommentMapper::toResponseDTO).toList(),
                topic.getCreatedAt(),
                topic.getUpdatedAt()
        );
    }
}
