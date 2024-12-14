package br.com.soupaulodev.forumhub.modules.forum.mapper;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumIllegalArgumentException;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.entity.ForumEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;

/**
 * Mapper class for converting between {@link ForumEntity} and Data Transfer Objects (DTOs).
 * <p>
 *     This class provides static methods to convert between {@link ForumEntity} objects and
 *     various DTO representations such as {@link ForumCreateRequestDTO}, {@link ForumUpdateRequestDTO},
 *     and {@link ForumResponseDTO}. These transformations help decouple the domain model from
 *     the API layer, ensuring proper data transfer between different components of the application.
 * </p>
 *
 * @author soupaulodev
 */
public class ForumMapper {

    /**
     * Converts a {@link ForumCreateRequestDTO} to a {@link ForumEntity}.
     * <p>
     *     This method maps the forum creation DTO, which contains the details of the new forum,
     *     to a corresponding {@link ForumEntity} for persistence in the database.
     * </p>
     *
     * @param dto the {@link ForumCreateRequestDTO} containing forum data to be converted
     * @param owner {@link UserEntity} the owner of the forum
     */
    public static ForumEntity toEntity(ForumCreateRequestDTO dto, UserEntity owner) {
        if(owner == null) {
            throw new ForumIllegalArgumentException("Owner cannot be null");
        }
        if ((dto.name() == null || dto.name().isEmpty() || dto.name().isBlank())
                || (dto.description() == null || dto.description().isEmpty() || dto.description().isBlank())) {
            throw new ForumIllegalArgumentException("Forum name and description cannot be empty");
        }
        return new ForumEntity(
                dto.name(),
                dto.description(),
                owner);
    }

    /**
     * Converts a {@link ForumUpdateRequestDTO} to a {@link ForumEntity}.
     * <p>
     *     This method maps the forum update DTO, which contains updated details of the forum,
     *     to a corresponding {@link ForumEntity} for modifying the forum's information in the database.
     * </p>
     *
     * @param dto the {@link ForumUpdateRequestDTO} containing updated forum data to be converted
     * @param owner {@link UserEntity} the owner of the forum
     */
    public static ForumEntity toEntity(ForumUpdateRequestDTO dto, UserEntity owner) {
        return new ForumEntity(
                dto.name(),
                dto.description(),
                owner);
    }

    /**
     * Converts a {@link ForumEntity} to a {@link ForumResponseDTO}.
     * <p>
     *     This method maps a {@link ForumEntity} to a {@link ForumResponseDTO}, which contains
     *     forum data that is ready to be sent as a response to API calls. The response DTO
     *     provides a simplified view of the forum, hiding sensitive information and internal details.
     * </p>
     *
     * @param entity the {@link ForumEntity} object containing forum data to be converted
     * @return the corresponding {@link ForumResponseDTO} with the mapped forum data
     */
    public static ForumResponseDTO toResponseDTO(ForumEntity entity) {
        if (entity == null || entity.getOwner() == null) {
            throw new ForumIllegalArgumentException("Forum owner cannot be null");
        }
        return new ForumResponseDTO(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getOwner().getId(),
            entity.getParticipants().size(),
            entity.getTopics().size(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
}
