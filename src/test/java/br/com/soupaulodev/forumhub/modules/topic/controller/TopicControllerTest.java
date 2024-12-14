package br.com.soupaulodev.forumhub.modules.topic.controller;

import br.com.soupaulodev.forumhub.modules.comment.controller.dto.CommentResponseDTO;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ForumNotFoundException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.TopicIllegalArgumentException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.TopicNotFoundException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UserNotFoundException;
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

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

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

        when(updateTopicUseCase.execute(any(UUID.class), any(TopicUpdateRequestDTO.class))).thenReturn(responseDTO);

        ResponseEntity<TopicResponseDTO> updatedTopic = topicController.updateTopic(topicId.toString(), requestDTO);

        assertEquals(200, updatedTopic.getStatusCodeValue());
        assertEquals(responseDTO, updatedTopic.getBody());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentTopic() {
        UUID topicId = UUID.randomUUID();

        when(updateTopicUseCase.execute(any(UUID.class), any(TopicUpdateRequestDTO.class))).thenThrow(new TopicNotFoundException());

        assertThrows(TopicNotFoundException.class, () -> topicController.updateTopic(topicId.toString(), new TopicUpdateRequestDTO("Topic Example", "Description Example")));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingTopicWithoutProvidingTitleOrContent() {
        UUID topicId = UUID.randomUUID();

        when(updateTopicUseCase.execute(any(UUID.class), any(TopicUpdateRequestDTO.class))).thenThrow(new TopicIllegalArgumentException("You must provide at least one field to update:\n- title\n- content"));

        assertThrows(TopicIllegalArgumentException.class, () -> topicController.updateTopic(topicId.toString(), new TopicUpdateRequestDTO(null, null)));
    }

    @Test
    void shouldDeleteTopicSuccessfully() {
        UUID topicId = UUID.randomUUID();

        ResponseEntity<Void> response = topicController.deleteTopic(topicId.toString());

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentTopic() {
        UUID topicId = UUID.randomUUID();

        doThrow(new TopicNotFoundException()).when(deleteTopicUseCase).execute(topicId);

        assertThrows(TopicNotFoundException.class, () -> topicController.deleteTopic(topicId.toString()));
    }
}