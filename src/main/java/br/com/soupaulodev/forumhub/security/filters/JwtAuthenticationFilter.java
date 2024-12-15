package br.com.soupaulodev.forumhub.security.filters;

import br.com.soupaulodev.forumhub.security.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

/**
 * This filter intercepts HTTP requests and processes JWT authentication.
 * It extracts the JWT token from the request's cookies, verifies its validity,
 * and if the token is valid, it sets the authentication information in the Spring Security context.
 * <p>
 * The filter is used to ensure that incoming requests are authenticated via JWT tokens and that the user's
 * authentication is properly stored in the {@link SecurityContextHolder}.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    /**
     * Constructor to inject required dependencies.
     *
     * @param userDetailsService The service responsible for loading user details during authentication.
     * @param jwtUtil The utility class used to handle JWT generation and validation.
     */
    public JwtAuthenticationFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Processes the incoming HTTP request, extracts the JWT token from the cookies, and sets the authentication
     * information in the Spring Security context.
     *
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @param chain The filter chain.
     * @throws ServletException If an error occurs during the filter processing.
     * @throws IOException If an error occurs during the filter processing.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        logger.info("Processing authentication for request: {}", request.getRequestURI());

        String jwt = extractJwtFromCookies(request);
        if (jwt == null || jwt.trim().isEmpty()) {
            logger.warn("JWT token not found in request");
            chain.doFilter(request, response);
            return;
        }

        try {
            String userId = jwtUtil.extractUserId(jwt);

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (!jwtUtil.isTokenExpired(jwt)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    UUID.fromString(userId),
                                    null,
                                    Collections.emptyList());
                    authenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (Exception e) {
            logger.error("JWT authentication failed: {}", e.getMessage());
        }

        chain.doFilter(request, response);
    }


    /**
     * Extracts the JWT token from the cookies in the HTTP request.
     * Searches through the cookies for one named "JWT_TOKEN" and returns its value if found.
     *
     * @param request The HTTP request.
     * @return The JWT token as a string, or null if the token is not found.
     */
    private String extractJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JWT_TOKEN".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
