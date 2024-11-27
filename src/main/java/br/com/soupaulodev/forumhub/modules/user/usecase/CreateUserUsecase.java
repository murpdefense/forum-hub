package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UserAlreadyExistsException;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUsecase {

    private final UserRepository userRepository;

    public CreateUserUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO execute(UserCreateRequestDTO requestDTO) {

        UserEntity userEntity = UserMapper.toEntity(requestDTO);

        userRepository.findByUsernameAndEmail(userEntity.getUsername(), userEntity.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException();
                });

        UserEntity userSaved = userRepository.save(userEntity);
        return UserMapper.toResponseDTO(userSaved);
    }
}
