package br.com.soupaulodev.forumhub.modules.comment.mapper;

import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentResponseDTO;
import br.com.soupaulodev.forumhub.modules.comment.entity.CommentEntity;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.mapper.TopicMapper;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between CommentEntity and DTOs.
 */
public class CommentMapper {

    /**
     * Converts a CommentCreateRequestDTO to a CommentEntity.
     *
     * @param dto the CommentCreateRequestDTO containing the comment data
     * @param userEntity the UserEntity representing the user who made the comment
     * @param topicEntity the TopicEntity representing the topic to which the comment belongs
     * @return a new CommentEntity with the provided data
     */
    public static CommentEntity toEntity(CommentCreateRequestDTO dto,
                                         UserEntity userEntity,
                                         TopicEntity topicEntity) {
        return new CommentEntity(dto.getContent(), userEntity, topicEntity);
    }

    /**
     * Converts a CommentEntity to a CommentResponseDTO.
     *
     * @param commentEntity the CommentEntity to convert
     * @return a new CommentResponseDTO with the data from the CommentEntity
     */
    public static CommentResponseDTO toResponseDTO(CommentEntity commentEntity) {

        UserResponseDTO user = UserMapper.toResponseDTO(commentEntity.getUser());
        TopicResponseDTO topic = TopicMapper.toResponseDTO(commentEntity.getTopic());
        UUID parentComment = commentEntity.getParentComment() != null ? commentEntity.getParentComment().getId() : null;

        Set<CommentResponseDTO> replies = commentEntity.getReplies().stream()
                .map(CommentMapper::toResponseDTO)
                .collect(Collectors.toSet());

        return new CommentResponseDTO(
                commentEntity.getId(),
                commentEntity.getContent(),
                user,
                topic,
                parentComment,
                replies,
                commentEntity.getCreatedAt(),
                commentEntity.getUpdatedAt());
    }
}