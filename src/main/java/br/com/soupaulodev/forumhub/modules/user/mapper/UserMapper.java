package br.com.soupaulodev.forumhub.modules.user.mapper;

import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserRole;

public class UserMapper {

    public static UserEntity toEntity(UserRequestDTO userRequestDTO) {
        return new UserEntity(
            userRequestDTO.getName(),
            userRequestDTO.getUsername(),
            userRequestDTO.getEmail(),
            userRequestDTO.getPassword(),
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
