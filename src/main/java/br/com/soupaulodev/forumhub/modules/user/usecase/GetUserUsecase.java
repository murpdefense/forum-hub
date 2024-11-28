package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UserNotFoundException;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for retrieving a user by their ID.
 */
@Service
public class GetUserUsecase {

    private final UserRepository userRepository;

    /**
     * Constructor for GetUserUsecase.
     *
     * @param userRepository the repository for user data
     */
    public GetUserUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Executes the use case to retrieve a user by their ID.
     *
     * @param id the unique identifier of the user to be retrieved
     * @return the response data transfer object containing the retrieved user data
     * @throws UserNotFoundException if no user with the given ID is found
     */
    public UserResponseDTO execute(UUID id) {

        UserEntity userFound = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        return UserMapper.toResponseDTO(userFound);
    }
}
