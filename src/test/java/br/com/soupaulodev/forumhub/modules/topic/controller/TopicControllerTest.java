package br.com.soupaulodev.forumhub.modules.topic.controller;

import br.com.soupaulodev.forumhub.modules.exception.usecase.*;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicDetailsResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.usecase.*;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link TopicController}.
 * This class contains tests for the topic-related operations, such as creating, listing, retrieving by ID, updating, and
 * deleting topics.
 *
 * <p>
 *     The {@link TopicControllerTest} class is responsible for testing the behavior of the {@link TopicController} class.
 *     The tests cover the following operations:
 *     <ul>
 *         <li>Creating a topic.</li>
 *         <li>Listing topics.</li>
 *         <li>Retrieving a topic by ID.</li>
 *         <li>Updating a topic.</li>
 *         <li>Deleting a topic.</li>
 *     </ul>
 * </p>
 *
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

    @InjectMocks
    private TopicController topicController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateTopicSucessfully() {
        UUID forumId = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();
        String creatorUsername = "creatorUsernameExample";

        TopicCreateRequestDTO requestDTO = new TopicCreateRequestDTO(
                "Topic Example",
                "Description Example",
                forumId.toString(),
                creatorId.toString());

        Instant now = Instant.now();
        TopicResponseDTO responseDTO = new TopicResponseDTO(
                UUID.randomUUID(),
                "Topic Example",
                "Description Example",
                forumId,
                creatorId,
                creatorUsername,
                0,
                0,
                now,
                now
        );

        when(createTopicUseCase.execute(any(TopicCreateRequestDTO.class))).thenReturn(responseDTO);

        ResponseEntity<TopicResponseDTO> createdTopic = topicController.createTopic(requestDTO);

        assertEquals(201, createdTopic.getStatusCodeValue());
        assertEquals(responseDTO, createdTopic.getBody());
        assertEquals(URI.create("/topics/" + responseDTO.id()), createdTopic.getHeaders().getLocation());
    }

    @Test
    void shouldThrowForumNotFoundExceptionWhenCreatingTopicWithNonExistentForum() {
        UUID forumId = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();

        TopicCreateRequestDTO requestDTO = new TopicCreateRequestDTO(
                "Topic Example",
                "Description Example",
                forumId.toString(),
                creatorId.toString());

        when(createTopicUseCase.execute(any(TopicCreateRequestDTO.class))).thenThrow(new ForumNotFoundException());

        assertThrows(ForumNotFoundException.class, () -> topicController.createTopic(requestDTO));
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenCreatingTopicWithNonExistentCreator() {
        UUID forumId = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();

        TopicCreateRequestDTO requestDTO = new TopicCreateRequestDTO(
                "Topic Example",
                "Description Example",
                forumId.toString(),
                creatorId.toString());

        when(createTopicUseCase.execute(any(TopicCreateRequestDTO.class))).thenThrow(new UserNotFoundException());

        assertThrows(UserNotFoundException.class, () -> topicController.createTopic(requestDTO));
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
                        0,
                        0,
                        Instant.now(),
                        Instant.now()
                )
        );

        when(listTopicsUseCase.execute(Page, Size)).thenReturn(topics);

        ResponseEntity<List<TopicResponseDTO>> listedTopics = topicController.listForumsPageable(Page, Size);

        assertEquals(200, listedTopics.getStatusCodeValue());
        assertEquals(topics, listedTopics.getBody());
        assertEquals(topics.size(), listedTopics.getBody().size());
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
                0,
                0,
                new ArrayList<>(),
                now,
                now
        );

        when(getTopicDetailsUseCase.execute(topicId)).thenReturn(topicDetails);

        ResponseEntity<TopicDetailsResponseDTO> topicDetailsResponse = topicController.getTopicDetails(topicId.toString());

        assertEquals(200, topicDetailsResponse.getStatusCodeValue());
        assertEquals(topicDetails, topicDetailsResponse.getBody());
    }

    @Test
    void shouldThrowExceptionWhenGettingNonExistentTopicDetails() {
        UUID topicId = UUID.randomUUID();

        when(getTopicDetailsUseCase.execute(topicId)).thenThrow(new TopicNotFoundException());

        assertThrows(TopicNotFoundException.class, () -> topicController.getTopicDetails(topicId.toString()));
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
                0,
                0,
                now,
                now
        );

        UUID authenticatedUserId = creatorId;

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(authenticatedUserId, null));

        when(updateTopicUseCase.execute(any(UUID.class), any(TopicUpdateRequestDTO.class), eq(authenticatedUserId)))
                .thenReturn(responseDTO);

        ResponseEntity<TopicResponseDTO> updatedTopic = topicController.updateTopic(topicId.toString(), requestDTO);

        assertEquals(200, updatedTopic.getStatusCodeValue());
        assertEquals(responseDTO, updatedTopic.getBody());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingTopicWhenUserIsNotTheCreator() {
        UUID topicId = UUID.randomUUID();
        UUID nonCreatorId = UUID.randomUUID();

        TopicUpdateRequestDTO requestDTO = new TopicUpdateRequestDTO(
                "Topic Example",
                "Description Example");

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(nonCreatorId, null));

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

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(authenticatedUserId, null));

        when(updateTopicUseCase.execute(any(UUID.class), any(TopicUpdateRequestDTO.class), eq(authenticatedUserId)))
                .thenThrow(new TopicNotFoundException());

        assertThrows(TopicNotFoundException.class, () -> topicController.updateTopic(topicId.toString(), requestDTO));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingTopicWithoutProvidingTitleOrContent() {
        UUID topicId = UUID.randomUUID();
        UUID authenticatedUserId = UUID.randomUUID();

        TopicUpdateRequestDTO requestDTO = new TopicUpdateRequestDTO(null, null);

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(authenticatedUserId, null));

        when(updateTopicUseCase.execute(any(UUID.class), any(TopicUpdateRequestDTO.class), eq(authenticatedUserId)))
                .thenThrow(new TopicIllegalArgumentException("You must provide at least one field to update:\n- title\n- content"));

        assertThrows(TopicIllegalArgumentException.class, () -> topicController.updateTopic(topicId.toString(), requestDTO));
    }

    @Test
    void shouldDeleteTopicSuccessfully() {
        UUID topicId = UUID.randomUUID();
        UUID authenticatedUserId = UUID.randomUUID();

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(authenticatedUserId, null));

        doNothing().when(deleteTopicUseCase).execute(any(UUID.class), eq(authenticatedUserId));

        ResponseEntity<Void> response = topicController.deleteTopic(topicId.toString());

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentTopic() {
        UUID topicId = UUID.randomUUID();
        UUID creatorId = UUID.randomUUID();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(creatorId, null));

        doThrow(new TopicNotFoundException()).when(deleteTopicUseCase).execute(topicId, creatorId);

        assertThrows(TopicNotFoundException.class, () -> topicController.deleteTopic(topicId.toString()));
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
}