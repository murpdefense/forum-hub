package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserDetailsResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case responsible for handling the retrieval of a user by their ID.
 * <p>
 * The {@link GetUserDetailsUseCase} class processes requests to retrieve a user by their unique identifier.
 * If successful, it returns the user data in a response data transfer object.
 * </p>
 * <p>
 * It interacts with the {@link UserRepository} to retrieve user data from the database.
 * If no user with the given ID is found, a {@link ResourceNotFoundException} is thrown.
 * </p>
 *
 * @author <a href="http://soupaulodev.com.br>soupaulodev</a>
 */
@Service
public class GetUserDetailsUseCase {

    private final UserRepository userRepository;

    /**
     * Constructs a new {@link GetUserDetailsUseCase}.
     *
     * @param userRepository The repository responsible for retrieving user data from the database.
     */
    public GetUserDetailsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Executes the use case to retrieve a user by their ID.
     * <p>
     * This method retrieves a user from the database using the provided unique identifier. If the user is found,
     * the user data is returned in a response data transfer object. If no user with the given ID is found,
     * a {@link ResourceNotFoundException} is thrown.
     * </p>
     *
     * @param id the user's unique identifier of type {@link UUID}
     * @return {@link UserResponseDTO} the data transfer object containing the retrieved user data
     * @throws ResourceNotFoundException if no user with the given ID is found
     */
    public UserDetailsResponseDTO execute(UUID id, UUID authenticatedUserId) {
        UserEntity userFound = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        if (!userFound.getId().equals(authenticatedUserId)) {
            userFound.setEmail(null);
        }

        return UserMapper.toDetailsResponseDTO(userFound);
    }
}
