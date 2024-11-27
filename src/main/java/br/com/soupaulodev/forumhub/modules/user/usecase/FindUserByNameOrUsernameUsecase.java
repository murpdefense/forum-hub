package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UserNotFoundException;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FindUserByNameOrUsernameUsecase {

    private final UserRepository userRepository;

    public FindUserByNameOrUsernameUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO execute(String nameOrUsername) {

         UserEntity userFound = userRepository.findByNameOrUsername(nameOrUsername, nameOrUsername)
                .orElseThrow(UserNotFoundException::new);

        return UserMapper.toResponseDTO(userFound);
    }
}
