package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UserIllegalArgumentException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UserNotFoundException;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UpdateUserUsecase {

    private final UserRepository userRepository;

    public UpdateUserUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO execute(UUID id, UserUpdateRequestDTO requestDTO) {
        UserEntity userDB = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (requestDTO == null
            || requestDTO.getName() == null
            && requestDTO.getUsername() == null
            && requestDTO.getEmail() == null
            && requestDTO.getPassword() == null) {

            throw new UserIllegalArgumentException("""
                You must provide at least one field to update:
                - name
                - username
                - email
                - password
                """);
        }
        if (requestDTO.getName() != null) {
            userDB.setName(requestDTO.getName());
        }
        if (requestDTO.getUsername() != null) {
            userDB.setUsername(requestDTO.getUsername());
        }
        if (requestDTO.getEmail() != null) {
            userDB.setEmail(requestDTO.getEmail());
        }
        if (requestDTO.getPassword() != null) {
            userDB.setPassword(requestDTO.getPassword());
        }

        userDB.setUpdatedAt(Instant.now());

        UserEntity userUpdated = userRepository.save(userDB);
        return UserMapper.toResponseDTO(userUpdated);
    }
}
