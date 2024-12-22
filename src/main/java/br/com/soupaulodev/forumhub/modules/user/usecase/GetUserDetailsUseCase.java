package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Use case responsible for retrieving user details based on the user's unique identifier (ID).
 * <p>
 * This service interacts with the {@link UserRepository} to fetch user data and returns the user details
 * in a {@link UserResponseDTO}. If no user is found with the provided ID, a {@link ResourceNotFoundException}
 * is thrown.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 * UserResponseDTO userResponseDTO = getUserDetailsUseCase.execute(userId);
 * </pre>
 * </p>
 *
 * @author <a href="http://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class GetUserDetailsUseCase {

    private final UserRepository userRepository;

    public GetUserDetailsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a user's details by their unique ID.
     * <p>
     * This method queries the {@link UserRepository} for a user with the given ID. If a user is found,
     * the user details are converted into a {@link UserResponseDTO} and returned. If no user is found
     * with the provided ID, a {@link ResourceNotFoundException} is thrown.
     * </p>
     *
     * @param id the unique ID of the user to be retrieved
     * @return {@link UserResponseDTO} containing the user's details
     * @throws ResourceNotFoundException if no user is found with the provided ID
     */
    public UserResponseDTO execute(UUID id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found."));

        return UserMapper.toResponseDTO(userEntity);
    }
}
