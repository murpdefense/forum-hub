package br.com.soupaulodev.forumhub.modules.user.controller;

import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.usecase.DeleteUserUseCase;
import br.com.soupaulodev.forumhub.modules.user.usecase.FindUserByNameOrUsernameUseCase;
import br.com.soupaulodev.forumhub.modules.user.usecase.GetUserUseCase;
import br.com.soupaulodev.forumhub.modules.user.usecase.UpdateUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/v1/user")
public class UserController {
    private final GetUserUseCase getUserUseCase;
    private final FindUserByNameOrUsernameUseCase findUserByNameOrUsernameUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    /**
     * Constructor for {@link UserController}.
     *
     * @param getUserUseCase {@link GetUserUseCase} the use case for handling user retrieval by ID
     * @param findUserByNameOrUsernameUsecase {@link FindUserByNameOrUsernameUseCase} the use case for handling user retrieval by name or username
     * @param updateUserUseCase {@link UpdateUserUseCase} the use case for handling user update operations
     * @param deleteUserUseCase {@link DeleteUserUseCase} the use case for handling user deletion operations
     */
    public UserController(GetUserUseCase getUserUseCase,
                          FindUserByNameOrUsernameUseCase findUserByNameOrUsernameUsecase,
                          UpdateUserUseCase updateUserUseCase,
                          DeleteUserUseCase deleteUserUseCase) {
        this.getUserUseCase = getUserUseCase;
        this.findUserByNameOrUsernameUseCase = findUserByNameOrUsernameUsecase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    /**
     * Endpoint for handling retrieval of a user by their unique identifier.
     * This method retrieves a user by their unique identifier and returns the user data.
     *
     * @param id the user's unique identifier of type {@link UUID}
     * @return a {@link ResponseEntity} of {@link UserResponseDTO} with status 200 (OK) and the user data
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@Valid @PathVariable("id") String id) {

        return ResponseEntity.ok(getUserUseCase.execute(UUID.fromString(id)));
    }

    /**
     * Endpoint for handling retrieval of a user by their name or username.
     * This method retrieves a user by their name or username and returns the user data.
     *
     * @param query the name or username of the {@link String} type user to be retrieved
     * @return a {@link ResponseEntity} of {@link UserResponseDTO} with status 200 (OK) and the user data
     */
    @GetMapping("/find/{nameOrUsername}")
    public ResponseEntity<UserResponseDTO> getUserByNameOrUsername(@PathVariable("nameOrUsername") String query) {

        return ResponseEntity.ok(findUserByNameOrUsernameUseCase.execute(query));
    }

    /**
     * Endpoint for handling user update operations.
     * This method updates a user by their unique identifier, using the data provided in the request DTO
     * and returns the updated user data.
     *
     * @param id the unique identifier of the user to be updated
     * @param requestDTO the data transfer object {@link UserUpdateRequestDTO} containing user update data
     * @return a {@link ResponseEntity} of {@link UserResponseDTO} with status 200 (OK) and the updated user data
     */
    @PutMapping("/{id}")
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@Valid @PathVariable("id") String id) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        deleteUserUseCase.execute(UUID.fromString(id), authenticatedUserId);
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
