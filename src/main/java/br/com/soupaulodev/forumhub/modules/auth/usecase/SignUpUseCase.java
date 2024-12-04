package br.com.soupaulodev.forumhub.modules.auth.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UserAlreadyExistsException;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import br.com.soupaulodev.forumhub.security.utils.CookieUtil;
import jakarta.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Use case responsible for handling the user registration process in the application.
 * <p>
 * The {@link SignUpUseCase} class handles the creation of new users in the system.
 * It checks if the username already exists, encodes the password, and saves the user to the database.
 * Upon successful registration, it generates a JWT token cookie for the newly created user.
 * </p>
 * <p>
 * If the username is already taken, an exception is thrown.
 * </p>
 *
 * @author soupaulodev
 */
@Service
public class SignUpUseCase {

    private final Logger logger = LoggerFactory.getLogger(SignUpUseCase.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CookieUtil cookieUtil;

    /**
     * Constructor for initializing the SignUpUseCase with the necessary dependencies.
     *
     * @param userRepository The repository used to interact with user data in the database.
     * @param passwordEncoder The password encoder used to securely hash user passwords.
     * @param cookieUtil Utility class for generating cookies containing JWT tokens.
     */
    public SignUpUseCase(UserRepository userRepository,
                         PasswordEncoder passwordEncoder,
                         CookieUtil cookieUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cookieUtil = cookieUtil;
    }

    /**
     * Handles the user registration process.
     * <p>
     * This method first checks if the username is already taken. If it is, a {@link UserAlreadyExistsException} is thrown.
     * If the username is available, the user is created by encoding their password and saving the user entity to the database.
     * After successful registration, a JWT token is generated and returned as a cookie.
     * </p>
     *
     * @param requestDTO The user registration data, including username, password, name, and email.
     * @return A {@link Cookie} containing the JWT token for the newly registered user.
     * @throws UserAlreadyExistsException If the username is already taken by another user.
     */
    public Cookie execute(UserCreateRequestDTO requestDTO) {
        if (userRepository.existsByUsername(requestDTO.username())) {
            logger.warn("Attempt to register with existing username: {}", requestDTO.username());
            throw new UserAlreadyExistsException();
        }

        UserEntity user = new UserEntity();
        user.setUsername(requestDTO.username());
        user.setPassword(passwordEncoder.encode(requestDTO.password()));
        user.setName(requestDTO.name());
        user.setEmail(requestDTO.email());

        UserEntity userCreated = userRepository.save(user);

        return cookieUtil.generateCookieWithToken(userCreated);
    }
}
