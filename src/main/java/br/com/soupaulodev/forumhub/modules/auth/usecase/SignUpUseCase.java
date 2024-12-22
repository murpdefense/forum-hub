package br.com.soupaulodev.forumhub.modules.auth.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceAlreadyExistsException;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import br.com.soupaulodev.forumhub.security.utils.CookieUtil;
import jakarta.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Handles the user registration process in the application.
 * <p>
 * This use case ensures that new users can register in the system by checking
 * if the username is available, securely hashing their password, saving the user,
 * and generating a JWT token as a cookie upon successful registration.
 * </p>
 * <p>
 * If the username already exists, a {@link ResourceAlreadyExistsException} is thrown.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class SignUpUseCase {

    private static final Logger logger = LoggerFactory.getLogger(SignUpUseCase.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CookieUtil cookieUtil;

    /**
     * Constructs a {@link SignUpUseCase} instance with the required dependencies.
     *
     * @param userRepository  Repository for interacting with user data.
     * @param passwordEncoder Encoder for securely hashing passwords.
     * @param cookieUtil      Utility class for generating JWT cookies.
     */
    public SignUpUseCase(UserRepository userRepository,
                         PasswordEncoder passwordEncoder,
                         CookieUtil cookieUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cookieUtil = cookieUtil;
    }

    /**
     * Registers a new user in the system.
     * <p>
     * This method verifies the availability of the username, securely encodes the password,
     * and saves the user to the database. Upon success, it generates a JWT token cookie.
     * </p>
     *
     * @param requestDTO The registration details (username, password, name, email).
     * @return A map containing the created user details and a JWT cookie.
     * @throws ResourceAlreadyExistsException If the username is already taken.
     */
    public Map<String, Object> execute(UserCreateRequestDTO requestDTO) {
        validateUsernameAvailability(requestDTO.username());

        UserEntity user = createUserEntity(requestDTO);
        userRepository.save(user);

        Cookie jwtCookie = cookieUtil.generateCookieWithToken(user);

        logger.info("User {} registered successfully", requestDTO.username());

        return Map.of(
                "user", UserMapper.toDetailsResponseDTO(user),
                "cookie", jwtCookie
        );
    }

    /**
     * Checks if the username is already taken.
     *
     * @param username The username to check.
     * @throws ResourceAlreadyExistsException If the username is already in use.
     */
    private void validateUsernameAvailability(String username) {
        if (userRepository.existsByUsername(username)) {
            logger.warn("Attempt to register with existing username: {}", username);
            throw new ResourceAlreadyExistsException("Username already exists");
        }
    }

    /**
     * Creates a {@link UserEntity} from the registration request.
     *
     * @param requestDTO The registration details.
     * @return The constructed {@link UserEntity}.
     */
    private UserEntity createUserEntity(UserCreateRequestDTO requestDTO) {
        UserEntity user = new UserEntity();
        user.setUsername(requestDTO.username());
        user.setPassword(passwordEncoder.encode(requestDTO.password()));
        user.setName(requestDTO.name());
        user.setEmail(requestDTO.email());

        return user;
    }
}
