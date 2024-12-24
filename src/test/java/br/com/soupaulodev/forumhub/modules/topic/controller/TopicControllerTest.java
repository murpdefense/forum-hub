package br.com.soupaulodev.forumhub.modules.topic.controller;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UnauthorizedException;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicDetailsResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.usecase.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
class TopicControllerTest {

    @Mock
    private CreateTopicUseCase createTopicUseCase;

    @Mock
    private ListTopicsUseCase listTopicsUseCase;

    @Mock
    private GetTopicDetailsUseCase getTopicDetailsUseCase;

    @Mock
    private UpdateTopicUseCase updateTopicUseCase;

    @Mock
    private DeleteTopicUseCase deleteTopicUseCase;

    @Mock
    private HighTopicUseCase highTopicUseCase;

    @Mock
    private UnHighTopicUseCase unHighTopicUseCase;

    @InjectMocks
    private TopicController topicController;

    private final UUID userId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(userId, null));
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldCreateTopicSucessfully() {
        UUID forumId = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        String creatorUsername = "creatorUsernameExample";

        TopicCreateRequestDTO requestDTO = new TopicCreateRequestDTO(
                "Topic Example",
                "Description Example",
                forumId.toString());

        Instant now = Instant.now();
        TopicResponseDTO responseDTO = new TopicResponseDTO(
                UUID.randomUUID(),
                "Topic Example",
                "Description Example",
                forumId,
                creatorId,
                creatorUsername,
                0L,
                0L,
                now,
                now
        );

        when(createTopicUseCase.execute(any(TopicCreateRequestDTO.class), any(UUID.class))).thenReturn(responseDTO);

        ResponseEntity<TopicResponseDTO> createdTopic = topicController.createTopic(requestDTO);

        assertEquals(201, createdTopic.getStatusCode().value());
        assertEquals(responseDTO, createdTopic.getBody());
        assertEquals(URI.create("/topics/" + responseDTO.id()), createdTopic.getHeaders().getLocation());
    }

    @Test
    void shouldThrowForumNotFoundExceptionWhenCreatingTopicWithNonExistentForum() {
        UUID forumId = UUID.randomUUID();

        TopicCreateRequestDTO requestDTO = new TopicCreateRequestDTO(
                "Topic Example",
                "Description Example",
                forumId.toString());

        when(createTopicUseCase.execute(any(TopicCreateRequestDTO.class), any(UUID.class)))
                .thenThrow(new ResourceNotFoundException("Topic not found"));

        assertThrows(ResourceNotFoundException.class, () -> topicController.createTopic(requestDTO));
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenCreatingTopicWithNonExistentCreator() {
        UUID forumId = UUID.randomUUID();

        TopicCreateRequestDTO requestDTO = new TopicCreateRequestDTO(
                "Topic Example",
                "Description Example",
                forumId.toString());

        when(createTopicUseCase.execute(any(TopicCreateRequestDTO.class), any(UUID.class)))
                .thenThrow(new ResourceNotFoundException("Topic not found"));

        assertThrows(ResourceNotFoundException.class, () -> topicController.createTopic(requestDTO));
    }

    @Test
    void shouldListTopicsSuccessfully() {
        int Page = 0;
        int Size = 10;

        List<TopicResponseDTO> topics = List.of(
                new TopicResponseDTO(
                        UUID.randomUUID(),
                        "Topic Example",
                        "Description Example",
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        "creatorUsernameExample",
                        0L,
                        0L,
                        Instant.now(),
                        Instant.now()
                )
        );

        when(listTopicsUseCase.execute(Page, Size)).thenReturn(topics);

        ResponseEntity<List<TopicResponseDTO>> listedTopics = topicController.listForumsPageable(Page, Size);

        assertEquals(200, listedTopics.getStatusCode().value());
        assertEquals(topics, listedTopics.getBody());
        assertEquals(topics.size(), Objects.requireNonNull(listedTopics.getBody()).size());
    }

    @Test
    void shouldGetTopicDetailsSuccessfully() {
        UUID topicId = UUID.randomUUID();
        Instant now = Instant.now();

        TopicDetailsResponseDTO topicDetails = new TopicDetailsResponseDTO(
                UUID.randomUUID(),
                "Topic Example",
                "Description Example",
                UUID.randomUUID(),
                UUID.randomUUID(),
                "creatorUsernameExample",
                0L,
                0L,
                new ArrayList<>(),
                now,
                now
        );

        when(getTopicDetailsUseCase.execute(topicId)).thenReturn(topicDetails);

        ResponseEntity<TopicDetailsResponseDTO> topicDetailsResponse = topicController.getTopicDetails(topicId.toString());

        assertEquals(200, topicDetailsResponse.getStatusCode().value());
        assertEquals(topicDetails, topicDetailsResponse.getBody());
    }

    @Test
    void shouldThrowExceptionWhenGettingNonExistentTopicDetails() {
        UUID topicId = UUID.randomUUID();

        when(getTopicDetailsUseCase.execute(topicId)).thenThrow(new ResourceNotFoundException("Topic not found"));

        assertThrows(ResourceNotFoundException.class, () -> topicController.getTopicDetails(topicId.toString()));
    }

    @Test
    void shouldUpdateTopicSuccessfully() {
        UUID topicId = UUID.randomUUID();
        UUID forumId = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        String creatorUsername = "creatorUsernameExample";

        TopicUpdateRequestDTO requestDTO = new TopicUpdateRequestDTO(
                "Topic Example",
                "Description Example");

        Instant now = Instant.now();
        TopicResponseDTO responseDTO = new TopicResponseDTO(
                UUID.randomUUID(),
                "Topic Example Updated",
                "Description Example Updated",
                forumId,
                creatorId,
                creatorUsername,
                0L,
                0L,
                now,
                now
        );

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(creatorId, null));

        when(updateTopicUseCase.execute(any(UUID.class), any(TopicUpdateRequestDTO.class), eq(creatorId)))
                .thenReturn(responseDTO);

        ResponseEntity<TopicResponseDTO> updatedTopic = topicController.updateTopic(topicId.toString(), requestDTO);

        assertEquals(200, updatedTopic.getStatusCode().value());
        assertEquals(responseDTO, updatedTopic.getBody());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingTopicWhenUserIsNotTheCreator() {
        UUID topicId = UUID.randomUUID();
        UUID nonCreatorId = UUID.randomUUID();

        TopicUpdateRequestDTO requestDTO = new TopicUpdateRequestDTO(
                "Topic Example",
                "Description Example");

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(nonCreatorId, null));

        when(updateTopicUseCase.execute(any(UUID.class), any(TopicUpdateRequestDTO.class), eq(nonCreatorId)))
                .thenThrow(new UnauthorizedException("You are not allowed to update this topic."));

        assertThrows(UnauthorizedException.class, () -> topicController.updateTopic(topicId.toString(), requestDTO));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentTopic() {
        UUID topicId = UUID.randomUUID();
        UUID authenticatedUserId = UUID.randomUUID();

        TopicUpdateRequestDTO requestDTO = new TopicUpdateRequestDTO(
                "Topic Example",
                "Description Example");

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(authenticatedUserId, null));

        when(updateTopicUseCase.execute(any(UUID.class), any(TopicUpdateRequestDTO.class), eq(authenticatedUserId)))
                .thenThrow(new ResourceNotFoundException("Topic not found"));

        assertThrows(ResourceNotFoundException.class, () -> topicController.updateTopic(topicId.toString(), requestDTO));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingTopicWithoutProvidingTitleOrContent() {
        UUID topicId = UUID.randomUUID();
        UUID authenticatedUserId = UUID.randomUUID();

        TopicUpdateRequestDTO requestDTO = new TopicUpdateRequestDTO(null, null);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(authenticatedUserId, null));

        when(updateTopicUseCase.execute(any(UUID.class), any(TopicUpdateRequestDTO.class), eq(authenticatedUserId)))
                .thenThrow(new IllegalArgumentException("You must provide at least one field to update:\n- title\n- content"));

        assertThrows(IllegalArgumentException.class, () -> topicController.updateTopic(topicId.toString(), requestDTO));
    }

    @Test
    void shouldDeleteTopicSuccessfully() {
        UUID topicId = UUID.randomUUID();
        UUID authenticatedUserId = UUID.randomUUID();

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(authenticatedUserId, null));

        doNothing().when(deleteTopicUseCase).execute(any(UUID.class), eq(authenticatedUserId));

        ResponseEntity<Void> response = topicController.deleteTopic(topicId.toString());

        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentTopic() {
        UUID topicId = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(creatorId, null));

        doThrow(new ResourceNotFoundException("Topic not found")).when(deleteTopicUseCase).execute(topicId, creatorId);

        assertThrows(ResourceNotFoundException.class, () -> topicController.deleteTopic(topicId.toString()));
    }

    @Test
    void shouldThrowExceptionWhenDeleteTopicWhenUserIsNotTheCreator() {
        UUID topicId = UUID.randomUUID();
        UUID nonCreatorId = UUID.randomUUID();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(nonCreatorId, null));

        doThrow(new UnauthorizedException("You are not allowed to update this topic."))
                .when(deleteTopicUseCase).execute(any(UUID.class), eq(nonCreatorId));

        assertThrows(UnauthorizedException.class, () -> topicController.deleteTopic(topicId.toString()));
    }

    @Test
    void shouldHighTopicSuccessfully() {
        UUID topicId = UUID.randomUUID();
        UUID authenticatedUserId = UUID.randomUUID();

        doNothing().when(highTopicUseCase).execute(any(UUID.class), eq(authenticatedUserId));

        ResponseEntity<Void> response = topicController.highTopic(topicId.toString());

        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void shouldUnHighTopicSuccessfully() {
        UUID topicId = UUID.randomUUID();
        UUID authenticatedUserId = UUID.randomUUID();

        doNothing().when(unHighTopicUseCase).execute(any(UUID.class), eq(authenticatedUserId));

        ResponseEntity<Void> response = topicController.unHighTopic(topicId.toString());

        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());
    }
}