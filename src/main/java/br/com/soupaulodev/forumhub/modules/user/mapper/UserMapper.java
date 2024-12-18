package br.com.soupaulodev.forumhub.modules.user.mapper;

import br.com.soupaulodev.forumhub.modules.user.controller.dto.*;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;

/**
 * Mapper class for converting between {@link UserEntity} and Data Transfer Objects (DTOs).
 * <p>
 * This class provides static methods to convert between {@link UserEntity} objects and
 * various DTO representations such as {@link UserCreateRequestDTO}, {@link UserUpdateRequestDTO},
 * and {@link UserResponseDTO}. These transformations help decouple the domain model from
 * the API layer, ensuring proper data transfer between different components of the application.
 * </p>
 *
 * @author soupaulodev
 */
public class UserMapper {

    /**
     * Converts a {@link UserCreateRequestDTO} to a {@link UserEntity}.
     * <p>
     * This method maps the user creation DTO, which contains the details of the new user,
     * to a corresponding {@link UserEntity} for persistence in the database.
     * </p>
     *
     * @param dto the {@link UserCreateRequestDTO} containing user data to be converted.
     * @return the corresponding {@link UserEntity} with the mapped user data.
     */
    public static UserEntity toEntity(UserCreateRequestDTO dto) {
        return new UserEntity(
                dto.name(),
                dto.username(),
                dto.email(),
                dto.password()
        );
    }

    /**
     * Converts a {@link UserUpdateRequestDTO} to a {@link UserEntity}.
     * <p>
     * This method maps the user update DTO, which contains updated details of the user,
     * to a corresponding {@link UserEntity} for modifying the user's information in the database.
     * </p>
     *
     * @param dto the {@link UserUpdateRequestDTO} containing updated user data to be converted.
     * @return the corresponding {@link UserEntity} with the updated user data.
     */
    public static UserEntity toEntity(UserUpdateRequestDTO dto) {
        return new UserEntity(
                dto.name(),
                dto.username(),
                dto.email(),
                dto.password()
        );
    }

    /**
     * Converts a {@link UserEntity} to a {@link UserResponseDTO}.
     * <p>
     * This method maps a {@link UserEntity} to a {@link UserResponseDTO}, which contains
     * user data that is ready to be sent in the response of an API call. The response DTO
     * typically includes information such as the user ID, name, username, email, and timestamps.
     * </p>
     *
     * @param entity the {@link UserEntity} object containing user data to be converted.
     * @return the corresponding {@link UserResponseDTO} with the user's details.
     */
    public static UserResponseDTO toResponseDTO(UserEntity entity) {
        return new UserResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getUsername(),
                entity.getHighsCount(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static UserDetailsResponseDTO toDetailsResponseDTO(UserEntity entity) {
        return new UserDetailsResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getOwnedForums().stream().map(OwnerOfDTO::from).toList(),
                entity.getParticipatingForums().stream().map(ParticipatesInDTO::from).toList(),
                entity.getHighsCount(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
