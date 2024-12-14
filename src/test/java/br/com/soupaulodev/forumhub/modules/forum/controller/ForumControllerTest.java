package br.com.soupaulodev.forumhub.modules.forum.controller;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumAlreadyExistsException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumIllegalArgumentException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumNotFoundException;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.forum.usecase.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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

    @Test
    void shouldCreateForumSuccessfully() {
        ForumCreateRequestDTO requestDTO = new ForumCreateRequestDTO(
                "Forum Example",
                "Description Example",
                UUID.randomUUID().toString());

        Instant now = Instant.now();
        ForumResponseDTO responseDTO = new ForumResponseDTO(
                UUID.randomUUID(),
                "Forum Example",
                "Description Example",
                UUID.randomUUID(),
                1,
                0,
                now,
                now);

        when(createForumUseCase.execute(any(ForumCreateRequestDTO.class))).thenReturn(responseDTO);

        ResponseEntity<ForumResponseDTO> response = forumController.createForum(requestDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
        assertEquals(URI.create("/forums/" + responseDTO.id()), response.getHeaders().getLocation());
    }

    @Test
    void shouldThrowAlreadyExistsExceptionWhenCreatingForumWithExistingName() {

        ForumCreateRequestDTO requestDTO = new ForumCreateRequestDTO(
                "Forum Example",
                "Description Example",
                UUID.randomUUID().toString());

        when(createForumUseCase.execute(any(ForumCreateRequestDTO.class))).thenThrow(new ForumAlreadyExistsException());

        assertThrows(ForumAlreadyExistsException.class, () -> forumController.createForum(requestDTO));
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
                        1,
                        0,
                        now,
                        now),
                new ForumResponseDTO(
                        forumId,
                        "Forum Example",
                        "Description Example",
                        UUID.randomUUID(),
                        1,
                        0,
                        now,
                        now));

        when(listForumsUseCase.execute(page, size))
                .thenReturn(responseDTOS);

        ResponseEntity<List<ForumResponseDTO>> response = forumController.listForums(page, size);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTOS, response.getBody());
        assertEquals(2, response.getBody().size());
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
                1,
                0,
                now,
                now);

        when(getForumUseCase.execute(forumId)).thenReturn(responseDTO);

        ResponseEntity<ForumResponseDTO> response = forumController.getForum(forumId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void shouldGetForumThrowNotFoundException() {
        UUID forumId = UUID.randomUUID();

        when(getForumUseCase.execute(forumId)).thenThrow(new ForumNotFoundException());

        assertThrows(ForumNotFoundException.class, () -> forumController.getForum(forumId));
    }

    @Test
    void shouldUpdateForumSuccessfully() {
        UUID forumId = UUID.randomUUID();
        ForumUpdateRequestDTO requestDTO = new ForumUpdateRequestDTO(
                "Forum Example",
                "Description Example",
                UUID.randomUUID().toString());

        Instant now = Instant.now();
        ForumResponseDTO responseDTO = new ForumResponseDTO(
                forumId,
                "Forum Example",
                "Description Example",
                UUID.randomUUID(),
                1,
                0,
                now,
                now);

        when(updateForumUseCase.execute(forumId, requestDTO)).thenReturn(responseDTO);

        ResponseEntity<ForumResponseDTO> response = forumController.updateForum(forumId.toString(), requestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void shouldUpdateForumThrowNotFoundException() {
        UUID forumId = UUID.randomUUID();
        ForumUpdateRequestDTO requestDTO = new ForumUpdateRequestDTO(
                "Forum Example",
                "Description Example",
                UUID.randomUUID().toString());

        when(updateForumUseCase.execute(forumId, requestDTO)).thenThrow(new ForumNotFoundException());

        assertThrows(ForumNotFoundException.class, () -> forumController.updateForum(forumId.toString(), requestDTO));
    }

    @Test
    void shouldUpdateForumThrowForumIllegalArgumentException() {
        UUID forumId = UUID.randomUUID();
        ForumUpdateRequestDTO requestDTO = new ForumUpdateRequestDTO(
                "Forum Example",
                "Description Example",
                UUID.randomUUID().toString());

        when(updateForumUseCase.execute(forumId, requestDTO)).thenThrow(new ForumIllegalArgumentException(""));

        assertThrows(ForumIllegalArgumentException.class, () -> forumController.updateForum(forumId.toString(), requestDTO));
    }

    @Test
    void shouldDeleteForumSuccessfully() {
        UUID forumId = UUID.randomUUID();

        ResponseEntity<Void> response = forumController.deleteForum(forumId.toString());

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void shouldDeleteForumThrowNotFoundException() {
        UUID forumId = UUID.randomUUID();

        doThrow(new ForumNotFoundException()).when(deleteForumUseCase).execute(forumId);

        assertThrows(ForumNotFoundException.class, () -> forumController.deleteForum(forumId.toString()));
    }
}