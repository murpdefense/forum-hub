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

/**
 * Service class for updating a user's information.
 */
@Service
public class UpdateUserUsecase {

    private final UserRepository userRepository;

    /**
     * Constructor for UpdateUserUsecase.
     *
     * @param userRepository the repository for user data
     */
    public UpdateUserUsecase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Executes the use case to update a user's information.
     *
     * @param id the unique identifier of the user to be updated
     * @param requestDTO the data transfer object containing user update data
     * @return the response data transfer object containing updated user data
     * @throws UserNotFoundException if no user with the given ID is found
     * @throws UserIllegalArgumentException if no fields to update are provided
     */
    public UserResponseDTO execute(UUID id, UserUpdateRequestDTO requestDTO) {
        UserEntity userDB = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (requestDTO == null
            || requestDTO.name() == null
            && requestDTO.username() == null
            && requestDTO.email() == null
            && requestDTO.password() == null) {

            throw new UserIllegalArgumentException("""
                You must provide at least one field to update:
                - name
                - username
                - email
                - password
                """);
        }
        if (requestDTO.name() != null) {
            userDB.setName(requestDTO.name());
        }
        if (requestDTO.username() != null) {
            userDB.setUsername(requestDTO.username());
        }
        if (requestDTO.email() != null) {
            userDB.setEmail(requestDTO.email());
        }
        if (requestDTO.password() != null) {
            userDB.setPassword(requestDTO.password());
        }

        userDB.setUpdatedAt(Instant.now());

        UserEntity userUpdated = userRepository.save(userDB);
        return UserMapper.toResponseDTO(userUpdated);
    }
}
