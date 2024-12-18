package br.com.soupaulodev.forumhub.modules.forum.controller;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceAlreadyExistsException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UnauthorizedException;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.usecase.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link ForumController}.
 * This class contains tests for the forum-related operations, such as creating, listing, retrieving, updating, and deleting forums.
 *
 *
 * <p>
 * The {@link ForumControllerTest} class is responsible for testing the behavior of the {@link ForumController} class.
 * The tests cover the following operations:
 *     <ul>
 *         <li>Creating a forum.</li>
 *         <li>Listing forums.</li>
 *         <li>Rerieving a forum by its ID.</li>
 *         <li>Updating a forum.</li>
 *         <li>Deleting a forum.</li>
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
class ForumControllerTest {

    @Mock
    private CreateForumUseCase createForumUseCase;

    @Mock
    private ListForumsUseCase listForumsUseCase;

    @Mock
    private GetForumUseCase getForumUseCase;

    @Mock
    private UpdateForumUseCase updateForumUseCase;

    @Mock
    private DeleteForumUseCase deleteForumUseCase;

    @InjectMocks
    private ForumController forumController;

    @BeforeEach
    void setUpd() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldCreateForumSuccessfully() {
        ForumCreateRequestDTO requestDTO = new ForumCreateRequestDTO(
                "Forum Example",
                "Description Example");

        UUID userId = UUID.randomUUID();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null));

        Instant now = Instant.now();
        ForumResponseDTO responseDTO = new ForumResponseDTO(
                UUID.randomUUID(),
                "Forum Example",
                "Description Example",
                userId,
                0L,
                1,
                0,
                now,
                now);

        when(createForumUseCase.execute(any(ForumCreateRequestDTO.class), any(UUID.class))).thenReturn(responseDTO);

        ResponseEntity<ForumResponseDTO> response = forumController.createForum(requestDTO);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
        assertEquals(URI.create("/forums/" + responseDTO.id()), response.getHeaders().getLocation());
    }

    @Test
    void shouldThrowAlreadyExistsExceptionWhenCreatingForumWithExistingName() {

        ForumCreateRequestDTO requestDTO = new ForumCreateRequestDTO(
                "Forum Example",
                "Description Example");

        UUID userId = UUID.randomUUID();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null));

        when(createForumUseCase.execute(any(ForumCreateRequestDTO.class), any(UUID.class))).thenThrow(new ResourceAlreadyExistsException("Forum already exists"));

        assertThrows(ResourceAlreadyExistsException.class, () -> forumController.createForum(requestDTO));
    }

    @Test
    void shouldListForumsSuccessfully() {
        int page = 0;
        int size = 2;

        UUID forumId = UUID.randomUUID();
        Instant now = Instant.now();
        List<ForumResponseDTO> responseDTOS = List.of(
                new ForumResponseDTO(
                        forumId,
                        "Forum Example",
                        "Description Example",
                        UUID.randomUUID(),
                        0L,
                        1,
                        0,
                        now,
                        now),
                new ForumResponseDTO(
                        forumId,
                        "Forum Example",
                        "Description Example",
                        UUID.randomUUID(),
                        0L,
                        1,
                        0,
                        now,
                        now));

        when(listForumsUseCase.execute(page, size))
                .thenReturn(responseDTOS);

        ResponseEntity<List<ForumResponseDTO>> response = forumController.listForums(page, size);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(responseDTOS, response.getBody());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    void shouldGetForumSuccessfully() {
        UUID forumId = UUID.randomUUID();
        Instant now = Instant.now();
        ForumResponseDTO responseDTO = new ForumResponseDTO(
                forumId,
                "Forum Example",
                "Description Example",
                UUID.randomUUID(),
                0L,
                1,
                0,
                now,
                now);

        when(getForumUseCase.execute(forumId)).thenReturn(responseDTO);

        ResponseEntity<ForumResponseDTO> response = forumController.getForum(forumId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void shouldGetForumThrowNotFoundException() {
        UUID forumId = UUID.randomUUID();

        when(getForumUseCase.execute(forumId)).thenThrow(new ResourceNotFoundException("Forum not found"));

        assertThrows(ResourceNotFoundException.class, () -> forumController.getForum(forumId));
    }

    @Test
    void shouldUpdateForumSuccessfully() {
        UUID forumId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();

        ForumUpdateRequestDTO requestDTO = new ForumUpdateRequestDTO(
                "Forum Example",
                "Description Example");

        Instant now = Instant.now();
        ForumResponseDTO responseDTO = new ForumResponseDTO(
                forumId,
                "Forum Example",
                "Description Example",
                ownerId,
                0L,
                1,
                0,
                now,
                now);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(ownerId, null));

        when(updateForumUseCase.execute(forumId, requestDTO, ownerId)).thenReturn(responseDTO);

        ResponseEntity<ForumResponseDTO> response = forumController.updateForum(forumId.toString(), requestDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void shouldUpdateForumThrowNotFoundException() {
        UUID forumId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        ForumUpdateRequestDTO requestDTO = new ForumUpdateRequestDTO(
                "Forum Example",
                "Description Example");

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null));

        when(updateForumUseCase.execute(forumId, requestDTO, userId)).thenThrow(new ResourceNotFoundException("Forum not found"));

        assertThrows(ResourceNotFoundException.class, () -> forumController.updateForum(forumId.toString(), requestDTO));
    }

    @Test
    void shouldUpdateForumThrowForumIllegalArgumentException() {
        UUID forumId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();

        ForumUpdateRequestDTO requestDTO = new ForumUpdateRequestDTO(
                "Forum Example",
                "Description Example");

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(ownerId, null));

        when(updateForumUseCase.execute(forumId, requestDTO, ownerId)).thenThrow(new IllegalArgumentException(""));

        assertThrows(IllegalArgumentException.class, () -> forumController.updateForum(forumId.toString(), requestDTO));
    }

    @Test
    void shouldDeleteForumSuccessfully() {
        UUID forumId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(ownerId, null));

        ResponseEntity<Void> response = forumController.deleteForum(forumId.toString());

        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void shouldDeleteForumThrowNotFoundException() {
        UUID forumId = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(ownerId, null));

        doThrow(new ResourceNotFoundException("Forum not found")).when(deleteForumUseCase).execute(forumId, ownerId);

        assertThrows(ResourceNotFoundException.class, () -> forumController.deleteForum(forumId.toString()));
    }

    @Test
    void shouldThrowExceptionWhenDeleteForumWhenUserIsNotTheOwner() {
        UUID forumId = UUID.randomUUID();
        UUID nonOwnerId = UUID.randomUUID();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(nonOwnerId, null));

        doThrow(new UnauthorizedException("You are not allowed to update this topic."))
                .when(deleteForumUseCase).execute(any(UUID.class), eq(nonOwnerId));

        assertThrows(UnauthorizedException.class, () -> forumController.deleteForum(forumId.toString()));
    }
}