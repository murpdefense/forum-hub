package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UserNotFoundException;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * Use case responsible for handling the retrieval of a user by their name or username.
 * <p>
 *     The {@link FindUserByNameOrUsernameUseCase} class processes requests to retrieve a user by their name or username.
 *     If successful, it returns the user data in a response data transfer object.
 * </p>
 * <p>
 *     It interacts with the {@link UserRepository} to retrieve user data from the database.
 *     If no user with the given name or username is found, a {@link UserNotFoundException} is thrown.
 * </p>
 *
 * @author <a href="http://soupaulodev.com.br>soupaulodev</a>
 */
@Service
public class FindUserByNameOrUsernameUseCase {

    private final UserRepository userRepository;

    /**
     * Constructs a new {@link FindUserByNameOrUsernameUseCase}.
     *
     * @param userRepository The repository responsible for retrieving user data from the database.
     */
    public FindUserByNameOrUsernameUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Executes the use case to retrieve a user by their name or username.
     * <p>
     *     This method retrieves a user from the database using the provided name or username. If the user is found,
     *     the user data is returned in a response data transfer object. If no user with the given name or username is found,
     *     a {@link UserNotFoundException} is thrown.
     * </p>
     *
     * @param nameOrUsername the user's name or username of type {@link String}
     * @return {@link UserResponseDTO} the data transfer object containing the retrieved user data
     * @throws UserNotFoundException if no user with the given name or username is found
     */
    public UserResponseDTO execute(String nameOrUsername) {

         UserEntity userFound = userRepository.findByNameOrUsername(nameOrUsername, nameOrUsername)
                .orElseThrow(UserNotFoundException::new);

        return UserMapper.toResponseDTO(userFound);
    }
}
