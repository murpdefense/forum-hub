package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UserNotFoundException;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetUserUsecase {

    private final UserRepository userRepository;

    public GetUserUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO execute(UUID id) {

        UserEntity userFound = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        return UserMapper.toResponseDTO(userFound);
    }
}
