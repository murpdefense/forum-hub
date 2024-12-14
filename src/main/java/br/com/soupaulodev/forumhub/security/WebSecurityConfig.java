package br.com.soupaulodev.forumhub.security;

import br.com.soupaulodev.forumhub.security.filters.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Web Security Configuration Class.
 * <p>
 * This class defines the security settings for the web application, configuring authentication,
 * authorization, CORS, and session management. It also sets up filters such as JWT-based authentication
 * and integrates them into the Spring Security context. The configuration ensures that appropriate
 * endpoints are accessible to all users while others are protected and require authentication.
 * </p>
 *
 * Key features:
 * - Disables CSRF (Cross-Site Request Forgery) protection, as JWT is used for authentication.
 * - Configures CORS (Cross-Origin Resource Sharing) with a custom set of allowed origins, methods, and headers.
 * - Configures session management to be stateless (i.e., no session is created).
 * - Customizes access rules for different routes, allowing public access to certain endpoints while protecting others.
 * - Defines a password encoder and authentication manager to support secure login.
 *
 * The class also integrates the {@link JwtAuthenticationFilter} for JWT validation in HTTP requests.
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Value("${cors.allowed.origins}")
    private String allowedOrigins;

    private final JwtAuthenticationFilter jwtRequestFilter;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Constructor to initialize the security configuration with required dependencies.
     *
     * @param jwtRequestFilter The filter for JWT authentication.
     * @param customUserDetailsService Custom service for loading user details.
     */
    public WebSecurityConfig(JwtAuthenticationFilter jwtRequestFilter,
                             CustomUserDetailsService customUserDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Configures the HTTP security settings for the application, including authorization rules,
     * session management, and filtering.
     *
     * @param http The HTTP security configuration.
     * @return The configured {@link SecurityFilterChain}.
     * @throws Exception If any configuration error occurs.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/user/**", "/forums/**", "/topics/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
                        .requestMatchers("/v3/docs", "/v3/api-docs", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(customUserDetailsService)
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Provides a {@link PasswordEncoder} for encoding passwords using BCrypt.
     *
     * @return A {@link PasswordEncoder} instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides an {@link AuthenticationManager} bean for authentication-related operations.
     *
     * @param authenticationConfiguration The authentication configuration instance.
     * @return An {@link AuthenticationManager} bean.
     * @throws Exception If any error occurs during the manager creation.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configures CORS settings for the application, allowing specified origins, methods, and headers.
     *
     * @return A {@link CorsConfigurationSource} instance with the CORS configuration.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",\\s*")));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
