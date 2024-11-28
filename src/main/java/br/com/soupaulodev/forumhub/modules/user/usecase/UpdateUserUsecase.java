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
            || requestDTO.getName() == null
            && requestDTO.getUsername() == null
            && requestDTO.getEmail() == null
            && requestDTO.getPassword() == null) {

            throw new UserIllegalArgumentException("""
                You must provide at least one field to update:
                - name
                - username
                - email
                - password
                """);
        }
        if (requestDTO.getName() != null) {
            userDB.setName(requestDTO.getName());
        }
        if (requestDTO.getUsername() != null) {
            userDB.setUsername(requestDTO.getUsername());
        }
        if (requestDTO.getEmail() != null) {
            userDB.setEmail(requestDTO.getEmail());
        }
        if (requestDTO.getPassword() != null) {
            userDB.setPassword(requestDTO.getPassword());
        }

        userDB.setUpdatedAt(Instant.now());

        UserEntity userUpdated = userRepository.save(userDB);
        return UserMapper.toResponseDTO(userUpdated);
    }
}
