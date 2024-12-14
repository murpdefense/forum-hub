package br.com.soupaulodev.forumhub.modules.auth.usecase;

import br.com.soupaulodev.forumhub.modules.auth.controller.dto.LoginRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import br.com.soupaulodev.forumhub.security.utils.CookieUtil;
import jakarta.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Use case responsible for handling the login process for the application.
 * <p>
 * The {@link LoginUseCase} class processes login requests by authenticating the user credentials.
 * If successful, it generates and returns a cookie containing a JWT token for the authenticated user.
 * </p>
 * <p>
 * It interacts with the {@link AuthenticationManager} to authenticate the user and with the
 * {@link UserRepository} to retrieve user information from the database. If authentication is successful,
 * a JWT token is generated using the {@link CookieUtil} class, which is then sent as a cookie to the client.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class LoginUseCase {

    private static final Logger logger = LoggerFactory.getLogger(LoginUseCase.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CookieUtil cookieUtil;

    /**
     * Constructs a new {@link LoginUseCase} instance.
     *
     * @param userRepository The repository responsible for retrieving user data from the database.
     * @param authenticationManager The manager responsible for authenticating user credentials.
     * @param cookieUtil Utility class responsible for generating cookies with JWT tokens.
     */
    public LoginUseCase(UserRepository userRepository, AuthenticationManager authenticationManager, CookieUtil cookieUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.cookieUtil = cookieUtil;
    }

    /**
     * Executes the login process by authenticating the provided credentials and generating a JWT cookie.
     * <p>
     * This method authenticates the user by verifying the username and password. If the authentication is successful,
     * it generates a JWT token and returns it as a cookie. If authentication fails, an exception is thrown.
     * </p>
     *
     * @param requestDTO The login request containing the username and password to authenticate.
     * @return A {@link Cookie} containing the JWT token for the authenticated user.
     * @throws BadCredentialsException If the authentication fails due to invalid credentials.
     * @throws UsernameNotFoundException If the user is not found in the database.
     */
    public Cookie execute(LoginRequestDTO requestDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDTO.username(), requestDTO.password())
            );

            UserEntity user = userRepository.findByUsername(requestDTO.username())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return cookieUtil.generateCookieWithToken(user);
        } catch (AuthenticationException e) {
            logger.warn("Failed login attempt for username: {}", requestDTO.username());
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
