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
 * This class processes login requests by authenticating user credentials.
 * If successful, it generates and returns a cookie containing a JWT token.
 * It interacts with the AuthenticationManager for authentication and
 * UserRepository to retrieve user data. If authentication succeeds,
 * a JWT token is generated using the CookieUtil class.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class LoginUseCase {

    private static final Logger logger = LoggerFactory.getLogger(LoginUseCase.class);

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final CookieUtil cookieUtil;

    public LoginUseCase(UserRepository userRepository, AuthenticationManager authenticationManager, CookieUtil cookieUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.cookieUtil = cookieUtil;
    }

    /**
     * Executes the login process by authenticating the provided credentials
     * and generating a JWT cookie if successful.
     *
     * @param requestDTO The login request containing username and password.
     * @return A Cookie containing the JWT token for the authenticated user.
     * @throws BadCredentialsException   If authentication fails due to invalid credentials.
     * @throws UsernameNotFoundException If the user is not found in the database.
     */
    public Cookie execute(LoginRequestDTO requestDTO) {
        authenticateUser(requestDTO.username(), requestDTO.password());
        UserEntity user = getUserEntity(requestDTO.username());
        Cookie jwtCookie = cookieUtil.generateCookieWithToken(user);

        logger.info("User {} successfully authenticated and cookie generated.", requestDTO.username());
        return jwtCookie;
    }

    /**
     * Authenticates the user credentials using the AuthenticationManager.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @throws BadCredentialsException If authentication fails.
     */
    private void authenticateUser(String username, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            logger.debug("Authentication successful for username: {}", username);
        } catch (AuthenticationException ex) {
            logger.warn("Authentication failed for username: {}", username);
            throw new BadCredentialsException("Invalid username or password", ex);
        }
    }

    /**
     * Retrieves the UserEntity for the given username from the UserRepository.
     *
     * @param username The username of the user.
     * @return The UserEntity corresponding to the username.
     * @throws UsernameNotFoundException If the user is not found.
     */
    private UserEntity getUserEntity(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found: {}", username);
                    return new UsernameNotFoundException("User not found");
                });
    }
}
