package br.com.soupaulodev.forumhub.modules.user.controller;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UnauthorizedException;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.*;
import br.com.soupaulodev.forumhub.modules.user.usecase.DeleteUserUseCase;
import br.com.soupaulodev.forumhub.modules.user.usecase.GetUserDetailsUseCase;
import br.com.soupaulodev.forumhub.modules.user.usecase.ListUsersUseCase;
import br.com.soupaulodev.forumhub.modules.user.usecase.UpdateUserUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
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
    private ListUsersUseCase listUsersUseCase;

    @Mock
    private GetUserDetailsUseCase getUserDetailsUseCase;

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

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testGetAllUsers_shouldReturnAllUsers() {
        UUID userId = UUID.randomUUID();
        Instant now = Instant.now();
        List<UserResponseDTO> responseDTO = List.of(
                new UserResponseDTO(userId, "test-name", "test-username", 0L, now, now),
                new UserResponseDTO(UUID.randomUUID(), "test-name", "test-username",0L, now, now));
        int page = 0;
        int size = 10;

        when(listUsersUseCase.execute(page, size)).thenReturn(responseDTO);

        ResponseEntity<List<UserResponseDTO>> response = userController.listUsers(page, size);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
        assertEquals(responseDTO.size(), Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void testGetAllUsers_shouldReturnVoidListWhenNoUsersFound() {
        List<UserResponseDTO> responseDTO = List.of();
        int page = 0;
        int size = 10;

        when(listUsersUseCase.execute(page, size)).thenReturn(responseDTO);

        ResponseEntity<List<UserResponseDTO>> response = userController.listUsers(page, size);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
        assertEquals(0, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void testGetUserDetails_shouldReturnVoidListWhenPageIsNegative() {
        List<UserResponseDTO> responseDTO = List.of();
        int page = -1;
        int size = 10;

        when(listUsersUseCase.execute(page, size)).thenReturn(responseDTO);

        ResponseEntity<List<UserResponseDTO>> response = userController.listUsers(page, size);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void testGetUserDetails_shouldReturnVoidListWhenSizeIsNegative() {
        List<UserResponseDTO> responseDTO = List.of();
        int page = 0;
        int size = -1;

        when(listUsersUseCase.execute(page, size)).thenReturn(responseDTO);

        ResponseEntity<List<UserResponseDTO>> response = userController.listUsers(page, size);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void testGetUserDetails_shouldReturnUserDetailsByID() {
        UUID userId = UUID.randomUUID();
        Instant now = Instant.now();
        UserDetailsResponseDTO responseDTO = new UserDetailsResponseDTO(
                userId,
                "test-name",
                "test-username",
                null,
                List.of(new OwnerOfDTO(UUID.randomUUID(), "test-name", Instant.now())),
                List.of(new ParticipatesInDTO(UUID.randomUUID(), "test-name", Instant.now())),
                0L,
                now,
                now);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null));

        when(getUserDetailsUseCase.execute(any(UUID.class), any(UUID.class))).thenReturn(responseDTO);

        ResponseEntity<UserDetailsResponseDTO> response = userController.getUserById(UUID.randomUUID().toString());

        assertEquals(200, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void testGetUserDetails_shouldThrowUserNotFoundExceptionWhenUserNotFoundById() {
        UUID userId = UUID.randomUUID();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null));

        when(getUserDetailsUseCase.execute(any(UUID.class), any(UUID.class))).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> userController.getUserById(userId.toString()));
    }

    @Test
    void testGetUserDetails_shouldReturnUserDetailsWithoutEmail() {
        UUID userId = UUID.randomUUID();
        Instant now = Instant.now();
        UserDetailsResponseDTO responseDTO = new UserDetailsResponseDTO(
                userId,
                "test-name",
                "test-username",
                null,
                List.of(new OwnerOfDTO(UUID.randomUUID(), "test-name", Instant.now())),
                List.of(new ParticipatesInDTO(UUID.randomUUID(), "test-name", Instant.now())),
                0L,
                now,
                now);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(UUID.randomUUID(), null));

        when(getUserDetailsUseCase.execute(any(UUID.class), any(UUID.class))).thenReturn(responseDTO);

        ResponseEntity<UserDetailsResponseDTO> response = userController.getUserById(userId.toString());

        assertEquals(200, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void testUpdateUser_shouldUpdateUserSuccessfully() {
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
                0L,
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
    void testUpdateUser_shouldThrowExceptionWhenUpdatingUserIsNotSelf() {
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
    void testUpdateUser_shouldThrowExceptionWhenUpdatingUserWithNoFieldsToUpdate() {
        UUID userId = UUID.randomUUID();

        UserUpdateRequestDTO userUpdateRequestDTO = new UserUpdateRequestDTO(
                null,
                null,
                null,
                null);

        UUID authenticatedUserId = UUID.randomUUID();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(authenticatedUserId, null));

        when(updateUserUseCase.execute(any(UUID.class), any(UserUpdateRequestDTO.class), eq(authenticatedUserId)))
                .thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> userController.updateUser(userId.toString(), userUpdateRequestDTO));
    }

    @Test
    void testUpdateUser_shouldThrowExceptionWhenUserNotFoundWhenUpdatingUser() {
        UUID userId = UUID.randomUUID();

        UserUpdateRequestDTO userUpdateRequestDTO = new UserUpdateRequestDTO(
                "test-name",
                "test-username",
                "test@mail.com",
                "test-password");

        UUID authenticatedUserId = UUID.randomUUID();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(authenticatedUserId, null));

        when(updateUserUseCase.execute(any(UUID.class), any(UserUpdateRequestDTO.class), eq(authenticatedUserId)))
                .thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> userController.updateUser(userId.toString(), userUpdateRequestDTO));
    }

    @Test
    void testDeleteUser_shouldDeleteUserSuccessfully() {
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
    void testDeleteUser_shouldThrowExceptionWhenDeletingUserIsNotSelf() {
        UUID authenticatedUserId = UUID.randomUUID();

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(authenticatedUserId, null));

        doThrow(new UnauthorizedException("You are not allowed to delete this user."))
                .when(deleteUserUseCase).execute(any(UUID.class), eq(authenticatedUserId));

        assertThrows(UnauthorizedException.class, () -> userController.deleteUser(UUID.randomUUID().toString()));
    }

    @Test
    void testDeleteUser_shouldThrowExceptionWhenUserNotFoundWhenDeletingUser() {
        UUID authenticatedUserId = UUID.randomUUID();

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(authenticatedUserId, null));

        doThrow(new ResourceNotFoundException("User not found."))
                .when(deleteUserUseCase).execute(any(UUID.class), eq(authenticatedUserId));

        assertThrows(ResourceNotFoundException.class, () -> userController.deleteUser(UUID.randomUUID().toString()));
    }

}