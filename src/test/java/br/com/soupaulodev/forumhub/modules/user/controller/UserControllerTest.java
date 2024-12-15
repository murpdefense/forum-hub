package br.com.soupaulodev.forumhub.modules.user.controller;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UnauthorizedException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UserIllegalArgumentException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UserNotFoundException;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.usecase.DeleteUserUseCase;
import br.com.soupaulodev.forumhub.modules.user.usecase.FindUserByNameOrUsernameUseCase;
import br.com.soupaulodev.forumhub.modules.user.usecase.GetUserUseCase;
import br.com.soupaulodev.forumhub.modules.user.usecase.UpdateUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link UserController} class.
 * This class contains tests for the user-related operation, such as retrieving, updating, and deleting a user.
 *
 * <p>
 * The {@link UserController} class is responsible for testing the behavior of the {@link UserController} class.
 * The test cover the following operations:
 *     <ul>
 *         <li>Retrieving a user by their ID</li>
 *         <li>Retrieving a user by their name or username</li>
 *         <li>Updating a user by their ID</li>
 *         <li>Deleting a user by their ID</li>
 *     </ul>
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
class UserControllerTest {

    @Mock
    private GetUserUseCase getUserUseCase;

    @Mock
    private FindUserByNameOrUsernameUseCase findUserByNameOrUsernameUseCase;

    @Mock
    private UpdateUserUseCase updateUserUseCase;

    @Mock
    private DeleteUserUseCase deleteUserUseCase;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnUserById() {
        Instant now = Instant.now();
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                UUID.randomUUID(),
                "test-name",
                "test-username",
                "test@mail.com",
                now,
                now);

        when(getUserUseCase.execute(any(UUID.class))).thenReturn(userResponseDTO);

        ResponseEntity<UserResponseDTO> response = userController.getUserById(UUID.randomUUID().toString());

        assertEquals(200, response.getStatusCode().value());
        assertEquals(userResponseDTO, response.getBody());
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserNotFoundById() {
        when(getUserUseCase.execute(any(UUID.class))).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userController.getUserById(UUID.randomUUID().toString()));
    }

    @Test
    void shouldReturnUserByNameOrUsername() {
        Instant now = Instant.now();
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                UUID.randomUUID(),
                "test-name",
                "test-username",
                "test@mail.com",
                now,
                now);

        when(findUserByNameOrUsernameUseCase.execute(any(String.class))).thenReturn(userResponseDTO);

        ResponseEntity<UserResponseDTO> response = userController.getUserByNameOrUsername("test-name");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(userResponseDTO, response.getBody());
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserNotFoundByNameOrUsername() {
        when(findUserByNameOrUsernameUseCase.execute(any(String.class))).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userController.getUserByNameOrUsername("test-name"));
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        UUID userId = UUID.randomUUID();
        Instant now = Instant.now();

        UserUpdateRequestDTO userUpdateRequestDTO = new UserUpdateRequestDTO(
                "test-name",
                "test-username",
                "test@mail.com",
                "test-password");
        UserResponseDTO userResponseDTO = new UserResponseDTO(
                userId,
                "test-name",
                "test-username",
                "test@mail.com",
                now,
                now);

        UUID authenticatedUserId = UUID.randomUUID();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(authenticatedUserId, null));

        when(updateUserUseCase.execute(any(UUID.class), any(UserUpdateRequestDTO.class), eq(authenticatedUserId)))
                .thenReturn(userResponseDTO);

        ResponseEntity<UserResponseDTO> response = userController.updateUser(userId.toString(), userUpdateRequestDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(userResponseDTO, response.getBody());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingUserIsNotSelf() {
        UUID userId = UUID.randomUUID();

        UserUpdateRequestDTO userUpdateRequestDTO = new UserUpdateRequestDTO(
                "test-name",
                "test-username",
                "test@mail.com",
                "test-password");

        UUID authenticatedUserId = UUID.randomUUID();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(authenticatedUserId, null));

        when(updateUserUseCase.execute(any(UUID.class), any(UserUpdateRequestDTO.class), eq(authenticatedUserId)))
                .thenThrow(UnauthorizedException.class);

        assertThrows(UnauthorizedException.class, () -> userController.updateUser(userId.toString(), userUpdateRequestDTO));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingUserWithNoFieldsToUpdate() {
        UUID userId = UUID.randomUUID();

        UserUpdateRequestDTO userUpdateRequestDTO = new UserUpdateRequestDTO(
                null,
                null,
                null,
                null);

        UUID authenticatedUserId = UUID.randomUUID();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(authenticatedUserId, null));

        when(updateUserUseCase.execute(any(UUID.class), any(UserUpdateRequestDTO.class), eq(authenticatedUserId)))
                .thenThrow(UserIllegalArgumentException.class);

        assertThrows(UserIllegalArgumentException.class, () -> userController.updateUser(userId.toString(), userUpdateRequestDTO));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundWhenUpdatingUser() {
        UUID userId = UUID.randomUUID();

        UserUpdateRequestDTO userUpdateRequestDTO = new UserUpdateRequestDTO(
                "test-name",
                "test-username",
                "test@mail.com",
                "test-password");

        UUID authenticatedUserId = UUID.randomUUID();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(authenticatedUserId, null));

        when(updateUserUseCase.execute(any(UUID.class), any(UserUpdateRequestDTO.class), eq(authenticatedUserId)))
                .thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userController.updateUser(userId.toString(), userUpdateRequestDTO));
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        UUID userId = UUID.randomUUID();
        UUID authenticatedUserId = UUID.randomUUID();

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(authenticatedUserId, null));

        doNothing().when(deleteUserUseCase).execute(any(UUID.class), eq(authenticatedUserId));

        ResponseEntity<Void> response = userController.deleteUser(userId.toString());
        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void shouldThrowExceptionWhenDeletingUserIsNotSelf() {
        UUID authenticatedUserId = UUID.randomUUID();

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(authenticatedUserId, null));

        doThrow(new UnauthorizedException("You are not allowed to delete this user."))
                .when(deleteUserUseCase).execute(any(UUID.class), eq(authenticatedUserId));

        assertThrows(UnauthorizedException.class, () -> userController.deleteUser(UUID.randomUUID().toString()));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundWhenDeletingUser() {
        UUID authenticatedUserId = UUID.randomUUID();

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(authenticatedUserId, null));

        doThrow(new UserNotFoundException())
                .when(deleteUserUseCase).execute(any(UUID.class), eq(authenticatedUserId));

        assertThrows(UserNotFoundException.class, () -> userController.deleteUser(UUID.randomUUID().toString()));
    }

}