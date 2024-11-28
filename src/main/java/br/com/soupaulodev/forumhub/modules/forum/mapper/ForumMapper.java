package br.com.soupaulodev.forumhub.modules.forum.mapper;

import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Mapper class for converting between Forum DTOs and Forum entities.
 */
public class ForumMapper {

    /**
     * Converts a ForumCreateRequestDTO to a ForumEntity.
     *
     * @param dto the data transfer object containing the forum creation data
     * @param owner the owner of the forum
     * @return the created ForumEntity
     */
    public static ForumEntity toEntity(ForumCreateRequestDTO dto, UserEntity owner) {
        return new ForumEntity(dto.getName(),
                dto.getDescription(),
                owner);
    }

    /**
     * Converts a ForumUpdateRequestDTO to a ForumEntity.
     *
     * @param dto the data transfer object containing the forum update data
     * @param owner the owner of the forum
     * @return the updated ForumEntity
     */
    public static ForumEntity toEntity(ForumUpdateRequestDTO dto, UserEntity owner) {
        return new ForumEntity(dto.getName(),
                dto.getDescription(),
                owner);
    }

    /**
     * Converts a ForumEntity to a ForumResponseDTO.
     *
     * @param entity the forum entity to be converted
     * @return the ForumResponseDTO containing the forum data
     */
    public static ForumResponseDTO toResponseDTO(ForumEntity entity) {
        UserResponseDTO owner = UserMapper.toResponseDTO(entity.getOwner());

        Set<UserResponseDTO> participants = Stream.of(entity.getParticipants())
                .map(participant -> UserMapper.toResponseDTO((UserEntity) participant))
                .collect(Collectors.toSet());

        int topicCount = entity.getTopics().size();

        return new ForumResponseDTO(entity.getId(),
                entity.getName(),
                entity.getDescription(),
                owner,
                participants,
                topicCount,
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
