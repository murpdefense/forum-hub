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
 * Mapper class for converting between {@link CommentEntity} and Data Transfer Objects (DTOs).
 * <p>
 * This class provides static methods to convert between {@link CommentEntity} objects and
 * various DTO representations such as {@link CommentCreateRequestDTO} and {@link CommentResponseDTO}.
 * These transformations help decouple the domain model from the API layer, ensuring proper data transfer
 * between different components of the application.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public class CommentMapper {

    /**
     * Converts a {@link CommentCreateRequestDTO} to a {@link CommentEntity}.
     * <p>
     * This method maps the comment creation DTO, which contains the details of the new comment,
     * to a corresponding {@link CommentEntity} for persistence in the database.
     * </p>
     *
     * @param dto         the {@link CommentCreateRequestDTO} containing comment data to be converted
     * @param userEntity  the {@link UserEntity} associated with the comment
     * @param topicEntity the {@link TopicEntity} associated with the comment
     * @return a new {@link CommentEntity} with the data from the {@link CommentCreateRequestDTO}
     */
    public static CommentEntity toEntity(CommentCreateRequestDTO dto,
                                         UserEntity userEntity,
                                         TopicEntity topicEntity) {
        return new CommentEntity(dto.content(), userEntity, topicEntity);
    }

    /**
     * Converts a {@link CommentEntity} to a {@link CommentResponseDTO}.
     * <p>
     * This method maps the comment entity, which contains the details of the comment,
     * to a corresponding {@link CommentResponseDTO} for returning to the client.
     * </p>
     *
     * @param commentEntity the {@link CommentEntity} containing comment data to be converted
     * @return a new {@link CommentResponseDTO} with the data from the {@link CommentEntity}
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
                user.id(),
                topic.id(),
                commentEntity.getHighsCount(),
                parentComment,
                replies,
                commentEntity.getCreatedAt(),
                commentEntity.getUpdatedAt());
    }
}