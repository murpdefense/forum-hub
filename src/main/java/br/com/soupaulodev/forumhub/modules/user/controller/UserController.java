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
 *
 * @author <a href="https://soupaulodev.com.br>soupaulodev</a>
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

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve user details by their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User data retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") String id) {
        return ResponseEntity.ok(getUserDetailsUseCase.execute(UUID.fromString(id)));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all users", description = "Retrieve all users data with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users data retrieved successfully"),
    })
    public ResponseEntity<List<UserResponseDTO>> listUsers(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                           @RequestParam(defaultValue = "10") @Min(5) int size) {
        return ResponseEntity.ok(listUsersUseCase.execute(page, size));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update user details using their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User data updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable("id") String id,
                                                      @RequestBody @Valid UserUpdateRequestDTO requestDTO) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        return ResponseEntity.ok(updateUserUseCase.execute(UUID.fromString(id), requestDTO, authenticatedUserId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Delete a user by their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        deleteUserUseCase.execute(UUID.fromString(id), authenticatedUserId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/high/{id}")
    @Operation(summary = "High user", description = "Mark a user as 'high' by their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User marked as high successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    public ResponseEntity<Void> highUser(@PathVariable("id") String highedUserId) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        highUserUseCase.execute(UUID.fromString(highedUserId), authenticatedUserId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/unhigh/{id}")
    @Operation(summary = "Unhigh user", description = "Unmark a user as 'high' by their unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User unmarked as high successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    public ResponseEntity<Void> unHighUser(@PathVariable("id") String highedUserId) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        unHighUserUseCase.execute(UUID.fromString(highedUserId), authenticatedUserId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves the authenticated user's unique identifier from the security context.
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
