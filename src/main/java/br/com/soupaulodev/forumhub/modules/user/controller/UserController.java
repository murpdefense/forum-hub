package br.com.soupaulodev.forumhub.modules.user.controller;

import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for handling user-related operations.
 * This class provides endpoints for retrieving by ID, finding by name or username, updating, and deleting users.
 * The user-related operations are managed by interacting with the use cases retrieving by ID, finding by name or username,
 * updating, and deleting users.
 *
 * <p>
 * The {@link UserController} is responsible for:
 * <ul>
 *     <li>Handling user retrieval requests by ID.</li>
 *     <li>Handling user retrieval requests by name or username.</li>
 *     <li>Handling user update requests.</li>
 *     <li>Handling user deletion requests.</li>
 * </ul>
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Endpoints for user operations")
public class UserController {
    private final ListUsersUseCase listUsersUseCase;
    private final GetUserDetailsUseCase getUserDetailsUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final HighUserUseCase highUserUseCase;
    private final UnHighUserUseCase unHighUserUseCase;

    /**
     * Constructor for {@link UserController}.
     *
     * @param listUsersUseCase  {@link } the use case for handling all users retrieval
     * @param getUserUseCase    {@link GetUserDetailsUseCase} the use case for handling user retrieval by ID
     * @param updateUserUseCase {@link UpdateUserUseCase} the use case for handling user update operations
     * @param deleteUserUseCase {@link DeleteUserUseCase} the use case for handling user deletion operations
     * @param highUserUseCase {@link HighUserUseCase} the use case for handling user highing operations
     * @param unHighUserUseCase {@link UnHighUserUseCase} the use case for handling user unhighing operations
     */
    public UserController(ListUsersUseCase listUsersUseCase,
                          GetUserDetailsUseCase getUserUseCase,
                          UpdateUserUseCase updateUserUseCase,
                          DeleteUserUseCase deleteUserUseCase,
                          HighUserUseCase highUserUseCase,
                          UnHighUserUseCase unHighUserUseCase) {
        this.listUsersUseCase = listUsersUseCase;
        this.getUserDetailsUseCase = getUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.highUserUseCase = highUserUseCase;
        this.unHighUserUseCase = unHighUserUseCase;
    }

    /**
     * Endpoint for handling retrieval of a user by their unique identifier.
     * This method retrieves a user by their unique identifier and returns the user data.
     *
     * @param id the user's unique identifier of type {@link UUID}
     * @return a {@link ResponseEntity} of {@link UserResponseDTO} with status 200 (OK) and the user data
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Get user data by their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User data retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponseDTO> getUserById(@Valid
                                                       @PathVariable("id")
                                                       @org.hibernate.validator.constraints.UUID
                                                       String id) {
        return ResponseEntity.ok(getUserDetailsUseCase.execute(UUID.fromString(id)));
    }

    /**
     * Endpoint for handling retrieval of all users.
     * This method retrieves all users and returns a list of user data.
     *
     * @param page the page number to be retrieved (default is 0) and must be at least 0
     * @param size the number of items per page to be retrieved (default is 10) and must be at least 5
     * @return a list of {@link UserResponseDTO} with status 200 (OK) and the list of user data
     */
    @GetMapping("/all")
    @Operation(summary = "Get all users", description = "Get all users data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users data retrieved successfully"),
    })
    public ResponseEntity<List<UserResponseDTO>> listUsers(@Valid @RequestParam(defaultValue = "0") @Min(0) int page,
                                                           @Valid @RequestParam(defaultValue = "10") @Min(10) int size) {
        return ResponseEntity.ok(listUsersUseCase.execute(page, size));
    }

    /**
     * Endpoint for handling user update operations.
     * This method updates a user by their unique identifier, using the data provided in the request DTO
     * and returns the updated user data.
     *
     * @param id         the unique identifier of the user to be updated
     * @param requestDTO the data transfer object {@link UserUpdateRequestDTO} containing user update data
     * @return a {@link ResponseEntity} of {@link UserResponseDTO} with status 200 (OK) and the updated user data
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update user data by their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User data updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @PathVariable("id") String id,
                                                      @Valid @RequestBody UserUpdateRequestDTO requestDTO) {

        UUID authenticatedUserId = getAuthenticatedUserId();
        return ResponseEntity.ok(updateUserUseCase.execute(UUID.fromString(id), requestDTO, authenticatedUserId));
    }

    /**
     * Endpoint for handling user deletion operations.
     * This method deletes a user by their unique identifier.
     *
     * @param id the unique identifier of the user to be deleted
     * @return a {@link ResponseEntity} of {@link Void} with status 204 (NO_CONTENT) to indicate the successful deletion
     */
    @Operation(summary = "Delete user", description = "Delete user by their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@Valid @PathVariable("id") String id) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        deleteUserUseCase.execute(UUID.fromString(id), authenticatedUserId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint for handling highing a user.
     * This method highes a user by their unique identifier.
     *
     * @param highedUserId the unique identifier of the user to be highed
     * @return a {@link ResponseEntity} of {@link Void} with status 204 (NO_CONTENT) to indicate the successful highing
     */
    @Operation(summary = "High user", description = "High user by their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User highed successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    @PostMapping("/high/{id}")
    public ResponseEntity<Void> highUser(@Valid @PathVariable("id")
                                         @org.hibernate.validator.constraints.UUID String highedUserId) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        highUserUseCase.execute(UUID.fromString(highedUserId), authenticatedUserId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint for handling unhighing a user.
     * This method unhighes a user by their unique identifier.
     *
     * @param highedUserId the unique identifier of the user to be unhighed
     * @return a {@link ResponseEntity} of {@link Void} with status 204 (NO_CONTENT) to indicate the successful unhighing
     */
    @Operation(summary = "Unhigh user", description = "Unhigh user by their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User unhighed successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    @DeleteMapping("/high/{id}")
    public ResponseEntity<Void> unhighUser(@Valid @PathVariable("id")
                                         @org.hibernate.validator.constraints.UUID String highedUserId) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        unHighUserUseCase.execute(UUID.fromString(highedUserId), authenticatedUserId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves the authenticated user's unique identifier.
     *
     * @return the authenticated user's unique identifier
     */
    private UUID getAuthenticatedUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UUID) {
            return (UUID) principal;
        } else if (principal instanceof String) {
            return UUID.fromString((String) principal);
        }

        throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getName());
    }
}
