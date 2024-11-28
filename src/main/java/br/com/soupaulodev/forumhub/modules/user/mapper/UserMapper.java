package br.com.soupaulodev.forumhub.modules.user.mapper;

import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserRole;

/**
 * Mapper class for converting between User DTOs and User entities.
 */
public class UserMapper {

    /**
     * Converts a UserCreateRequestDTO to a UserEntity.
     *
     * @param dto the UserCreateRequestDTO containing user creation data
     * @return a UserEntity created from the provided DTO
     */
    public static UserEntity toEntity(UserCreateRequestDTO dto) {
        return new UserEntity(
            dto.getName(),
            dto.getUsername(),
            dto.getEmail(),
            dto.getPassword(),
            UserRole.USER
        );
    }

    /**
     * Converts a UserUpdateRequestDTO to a UserEntity.
     *
     * @param dto the UserUpdateRequestDTO containing user update data
     * @return a UserEntity created from the provided DTO
     */
    public static UserEntity toEntity(UserUpdateRequestDTO dto) {
        return new UserEntity(
                dto.getName(),
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                UserRole.USER
        );
    }

    /**
     * Converts a UserEntity to a UserResponseDTO.
     *
     * @param entity the UserEntity containing user data
     * @return a UserResponseDTO created from the provided entity
     */
    public static UserResponseDTO toResponseDTO(UserEntity entity) {
        return new UserResponseDTO(
                entity.getId(),
                entity.getName(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getRole(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
