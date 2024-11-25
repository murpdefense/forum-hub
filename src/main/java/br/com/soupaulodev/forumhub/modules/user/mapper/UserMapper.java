package br.com.soupaulodev.forumhub.modules.user.mapper;

import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserRole;

public class UserMapper {

    public static <T> UserEntity toEntity(T t) {
        return new UserEntity(
            ((UpdateUserRequestDTO) t).getName(),
            ((UpdateUserRequestDTO) t).getUsername(),
            ((UpdateUserRequestDTO) t).getEmail(),
            ((UpdateUserRequestDTO) t).getPassword(),
            UserRole.USER
        );
    }

    public static UserResponseDTO toResponseDTO(UserEntity user) {
        return new UserResponseDTO(
            user.getId(),
            user.getName(),
            user.getUsername(),
            user.getEmail()
        );
    }
}
