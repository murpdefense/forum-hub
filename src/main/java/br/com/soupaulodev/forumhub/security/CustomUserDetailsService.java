package br.com.soupaulodev.forumhub.security;

import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import br.com.soupaulodev.forumhub.security.utils.JwtUtil;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Custom implementation of the UserDetailsService interface, which is responsible for loading user-specific data
 * during the authentication process. This service interacts with the database to retrieve user information
 * based on the provided username and is used by Spring Security during authentication.
 * <p>
 * The service also provides functionality to generate JWT tokens for authenticated users.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    /**
     * Constructs a {@link CustomUserDetailsService} with the provided {@link UserRepository} and {@link JwtUtil}.
     *
     * @param userRepository The repository for querying user data from the database.
     * @param jwtUtil The utility class responsible for generating JWT tokens.
     */
    public CustomUserDetailsService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Loads a user by their username.
     * This method queries the UserRepository to retrieve a UserEntity based on the provided username.
     * If the user is found, a Spring Security User object is created and returned.
     * If the user is not found, a UsernameNotFoundException is thrown.
     *
     * @param username The username of the user to be loaded.
     * @return A UserDetails object containing the user’s credentials and authorities.
     * @throws UsernameNotFoundException If the user cannot be found in the database.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList()
        );
    }

    /**
     * Generates a JWT token for the given user.
     * This method uses the JwtUtil class to create a token that contains the user’s information.
     *
     * @param user The user for whom the token is to be generated.
     * @return A JWT token as a string.
     */
    public String generateToken(UserEntity user) {
        return jwtUtil.generateToken(user);
    }
}
