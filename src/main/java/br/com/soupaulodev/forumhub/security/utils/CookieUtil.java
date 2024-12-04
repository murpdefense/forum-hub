package br.com.soupaulodev.forumhub.security.utils;

import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.security.CustomUserDetailsService;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

/**
 * Utility class responsible for generating and managing cookies related to authentication.
 * <p>
 * The {@link CookieUtil} class is used to generate cookies containing JWT tokens for users.
 * It provides methods to create a cookie with the JWT token, which can be sent to the client
 * for subsequent authentication in secure HTTP requests.
 * </p>
 *
 * <p>
 * This class relies on the {@link CustomUserDetailsService} to generate the JWT token for the
 * user. The cookie is configured to be HTTP-only, secure, and have a maximum age of 7 days.
 * </p>
 *
 * @author soupaulodev
 */
@Component
public class CookieUtil {

    private final CustomUserDetailsService userDetailsService;

    /**
     * Constructs a new instance of the {@link CookieUtil} class.
     *
     * @param userDetailsService The service responsible for generating the JWT token for the user.
     */
    public CookieUtil(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Generates a cookie containing the JWT token for the specified user.
     * <p>
     * This method uses the {@link CustomUserDetailsService} to generate the JWT token for the user,
     * then creates a cookie with the token. The cookie is set to be HTTP-only, secure, and will expire
     * after 7 days.
     * </p>
     *
     * @param user The user for whom the JWT token is generated.
     * @return A cookie containing the JWT token, configured with the appropriate security settings.
     */
    public Cookie generateCookieWithToken(UserEntity user) {
        String jwt = userDetailsService.generateToken(user);

        Cookie cookie = new Cookie("JWT_TOKEN", jwt);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setPath("/");

        return cookie;
    }
}
