package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UserAlreadyExistsException;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for creating a new user.
 */
@Service
public class CreateUserUsecase {

    private final UserRepository userRepository;

    /**
     * Constructor for CreateUserUsecase.
     *
     * @param userRepository the repository for user data
     */
    public CreateUserUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Executes the use case to create a new user.
     *
     * @param requestDTO the data transfer object containing user creation data
     * @return the response data transfer object containing created user data
     * @throws UserAlreadyExistsException if a user with the same username and email already exists
     */
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
