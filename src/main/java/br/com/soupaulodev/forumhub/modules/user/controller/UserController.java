package br.com.soupaulodev.forumhub.modules.user.controller;

import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.usecase.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

/**
 * The role of the user.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final CreateUserUsecase createUserUsecase;
    private final GetUserUsecase getUserUsecase;
    private final FindUserByNameOrUsernameUsecase findUserByNameOrUsernameUsecase;
    private final UpdateUserUsecase updateUserUsecase;
    private final DeleteUserUsecase deleteUserUsecase;

    /**
     * Constructor for UserController.
     *
     * @param createUserUsecase the use case for creating a user
     * @param getUserUsecase the use case for retrieving a user by ID
     * @param findUserByNameOrUsernameUsecase the use case for finding a user by name or username
     * @param updateUserUsecase the use case for updating a user's information
     * @param deleteUserUsecase the use case for deleting a user
     */
    public UserController(CreateUserUsecase createUserUsecase,
                          GetUserUsecase getUserUsecase,
                          FindUserByNameOrUsernameUsecase findUserByNameOrUsernameUsecase,
                          UpdateUserUsecase updateUserUsecase,
                          DeleteUserUsecase deleteUserUsecase) {
        this.createUserUsecase = createUserUsecase;
        this.getUserUsecase = getUserUsecase;
        this.findUserByNameOrUsernameUsecase = findUserByNameOrUsernameUsecase;
        this.updateUserUsecase = updateUserUsecase;
        this.deleteUserUsecase = deleteUserUsecase;
    }

    /**
     * Creates a new user.
     *
     * @param requestDTO the data transfer object containing user creation data
     * @return the response entity containing the created user data
     */
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateRequestDTO requestDTO) {

        UserResponseDTO responseDTO = createUserUsecase.execute(requestDTO);
        URI uri = URI.create("/user/" + responseDTO.getId());
        return ResponseEntity.created(uri).body(responseDTO);
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return the response entity containing the user data
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") UUID id) {

        return ResponseEntity.ok(getUserUsecase.execute(id));
    }

    /**
     * Retrieves a user by their name or username.
     *
     * @param query the name or username of the user
     * @return the response entity containing the user data
     */
    @GetMapping("/{nameOrUsername}")
    public ResponseEntity<UserResponseDTO> getUserByNameOrUsername(@PathVariable("nameOrUsername") String query) {

        return ResponseEntity.ok(findUserByNameOrUsernameUsecase.execute(query));
    }

    /**
     * Updates a user's information.
     *
     * @param id the unique identifier of the user to be updated
     * @param requestDTO the data transfer object containing user update data
     * @return the response entity containing the updated user data
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable("id") UUID id,
                                      @Valid @RequestBody UserUpdateRequestDTO requestDTO) {

        return ResponseEntity.ok(updateUserUsecase.execute(id, requestDTO));
    }

    /**
     * Deletes a user by their unique identifier.
     *
     * @param id the unique identifier of the user to be deleted
     * @return the response entity indicating the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id) {

        deleteUserUsecase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
