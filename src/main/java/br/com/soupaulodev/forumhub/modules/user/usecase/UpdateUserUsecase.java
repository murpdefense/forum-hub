package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UnauthorizedException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UserIllegalArgumentException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UserNotFoundException;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

/**
 * Use case responsible for handling the update of a user's information.
 * <p>
 *     The {@link UpdateUserUsecase} class processes requests to update a user's information.
 *     If successful, it returns the updated user data in a response data transfer object.
 * </p>
 * <p>
 *     It interacts with the {@link UserRepository} to update user data in the database.
 *     If no user with the given ID is found, a {@link UserNotFoundException} is thrown.
 *     If no fields to update are provided, a {@link UserIllegalArgumentException} is thrown.
 * </p>
 *
 * @author <a href="http://soupaulodev.com.br>soupaulodev</a>
 */
@Service
public class UpdateUserUsecase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new {@link UpdateUserUsecase}.
     *
     * @param userRepository the repository responsible for updating user data in the database
     * @param passwordEncoder the password encoder used to securely hash user passwords
     */
    public UpdateUserUsecase(UserRepository userRepository,
                             PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Executes the use case to update a user's information.
     * <p>
     *     This method updates a user's information in the database using the provided unique identifier and update data.
     *     If the user is found and the update data is valid, the updated user data is returned in a response data transfer object.
     * </p>
     *
     * @param id the user's unique identifier of type {@link UUID}
     * @param requestDTO {@link UserUpdateRequestDTO} the data transfer object containing the user's update data
     * @param authenticatedUserId the authenticated user's unique identifier
     * @return {@link UserResponseDTO} the data transfer object containing the updated user data
     * @throws UserNotFoundException if no user with the given ID is found
     * @throws UserIllegalArgumentException if no fields to update are provided
     */
    public UserResponseDTO execute(UUID id, UserUpdateRequestDTO requestDTO, UUID authenticatedUserId) {
        UserEntity userDB = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (!userDB.getId().equals(authenticatedUserId)) {
            throw new UnauthorizedException("You are not allowed to update this user.");
        }

        if (requestDTO == null
            || (requestDTO.name() == null
            && requestDTO.username() == null
            && requestDTO.email() == null
            && requestDTO.password() == null)) {

            throw new UserIllegalArgumentException("""
                You must provide at least one field to update:
                - name
                - username
                - email
                - password
                """);
        }

        userDB.setName(requestDTO.name() != null ? requestDTO.name() : userDB.getName());
        userDB.setUsername(requestDTO.username() != null ? requestDTO.username() : userDB.getUsername());
        userDB.setEmail(requestDTO.email() != null ? requestDTO.email() : userDB.getEmail());
        userDB.setPassword(requestDTO.password() != null ?
                passwordEncoder.encode(requestDTO.password()) : userDB.getPassword());
        userDB.setUpdatedAt(Instant.now());

        return UserMapper.toResponseDTO(userRepository.save(userDB));
    }
}
