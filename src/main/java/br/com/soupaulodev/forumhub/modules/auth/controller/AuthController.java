package br.com.soupaulodev.forumhub.modules.auth.controller;

import br.com.soupaulodev.forumhub.modules.auth.controller.dto.LoginRequestDTO;
import br.com.soupaulodev.forumhub.modules.auth.usecase.LoginUseCase;
import br.com.soupaulodev.forumhub.modules.auth.usecase.LogoutUseCase;
import br.com.soupaulodev.forumhub.modules.auth.usecase.SignUpUseCase;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserCreateRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for handling authentication-related endpoints.
 * This controller provides endpoints for user login, registration (signup), and logout.
 * The authentication process is managed by interacting with the use cases for login, signup, and logout.
 *
 * <p>
 * The {@link AuthController} is responsible for:
 * - Handling user login requests and generating JWT tokens upon successful authentication.
 * - Handling user registration requests, ensuring the username is unique and then creating the user.
 * - Handling logout requests, invalidating the JWT token by clearing the associated cookie.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final LoginUseCase loginUseCase;
    private final SignUpUseCase signUpUseCase;
    private final LogoutUseCase logoutUseCase;

    /**
     * Constructor for the AuthController.
     * Initializes the controller with the necessary use cases for login, signup, and logout.
     *
     * @param loginUseCase  the use case for handling login operations
     * @param signUpUseCase the use case for handling user signup operations
     * @param logoutUseCase the use case for handling logout operations
     */
    public AuthController(LoginUseCase loginUseCase,
                          SignUpUseCase signUpUseCase,
                          LogoutUseCase logoutUseCase) {
        this.loginUseCase = loginUseCase;
        this.signUpUseCase = signUpUseCase;
        this.logoutUseCase = logoutUseCase;
    }

    /**
     * Endpoint for handling user login.
     * This method authenticates the user based on the provided credentials and returns a JWT token.
     * The token is set in the response as a cookie.
     *
     * @param requestDTO the login request containing username and password
     * @param response   the HTTP response to which the JWT token will be added as a cookie
     * @return a ResponseEntity with status 200 (OK) if login is successful
     */
    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticate a user and return a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO requestDTO, HttpServletResponse response) {
        response.addCookie(loginUseCase.execute(requestDTO));

        logger.info("User {} logged in successfully", requestDTO.username());
        return ResponseEntity.ok("User logged in successfully");
    }

    /**
     * Endpoint for handling user registration (signup).
     * This method registers a new user by accepting the user's details and creating the user.
     * A JWT token is generated and added to the response as a cookie.
     *
     * @param signUpRequest the user details for signup
     * @param response      the HTTP response to which the JWT token will be added as a cookie
     * @return a ResponseEntity with status 200 (OK) if registration is successful
     */
    @PostMapping("/signup")
    @Operation(summary = "Sign Up", description = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Username already exists")
    })
    public ResponseEntity<String> signUp(@Valid @RequestBody UserCreateRequestDTO signUpRequest, HttpServletResponse response) {
        response.addCookie(signUpUseCase.execute(signUpRequest));

        logger.info("User {} registered successfully", signUpRequest.username());
        return ResponseEntity.ok("User registered successfully");
    }

    /**
     * Endpoint for handling user logout.
     * This method invalidates the user's JWT token by clearing the associated cookie.
     *
     * @param response the HTTP response in which the JWT cookie will be removed
     * @return a ResponseEntity with status 200 (OK) indicating the user has been logged out
     */
    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Invalidate the user's JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged out successfully")
    })
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        response.addCookie(logoutUseCase.execute());

        logger.info("User logged out successfully");
        return ResponseEntity.ok().build();
    }
}
