package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UserNotFoundException;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for finding a user by their name or username.
 */
@Service
public class FindUserByNameOrUsernameUsecase {

    private final UserRepository userRepository;

    /**
     * Constructor for FindUserByNameOrUsernameUsecase.
     *
     * @param userRepository the repository for user data
     */
    public FindUserByNameOrUsernameUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Executes the use case to find a user by their name or username.
     *
     * @param nameOrUsername the name or username of the user to be found
     * @return the response data transfer object containing found user data
     * @throws UserNotFoundException if no user with the given name or username is found
     */
    public UserResponseDTO execute(String nameOrUsername) {

         UserEntity userFound = userRepository.findByNameOrUsername(nameOrUsername, nameOrUsername)
                .orElseThrow(UserNotFoundException::new);

        return UserMapper.toResponseDTO(userFound);
    }
}
