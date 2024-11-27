package br.com.soupaulodev.forumhub.modules.user.mapper;

import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserRole;

public class UserMapper {

    public static UserEntity toEntity(UserCreateRequestDTO dto) {
        return new UserEntity(
            dto.getName(),
            dto.getUsername(),
            dto.getEmail(),
            dto.getPassword(),
            UserRole.USER
        );
    }

    public static UserEntity toEntity(UserUpdateRequestDTO dto) {
        return new UserEntity(
                dto.getName(),
                dto.getUsername(),
                dto.getEmail(),
                dto.getPassword(),
                UserRole.USER
        );
    }

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
