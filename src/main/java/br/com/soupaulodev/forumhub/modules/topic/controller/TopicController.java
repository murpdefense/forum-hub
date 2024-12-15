package br.com.soupaulodev.forumhub.modules.topic.controller;

import br.com.soupaulodev.forumhub.modules.forum.controller.dto.ForumResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicDetailsResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.usecase.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * Controller for handling topic-related operations.
 * This class provides endpoints for creating, retrieving by ID, listing, updating, and deleting topics.
 * The topic-related operations are managed by interacting with the use cases for creating, retrieving by ID, listing,
 * updating, and deleting forums.
 *
 * <p>
 *     The {@link TopicController} is responsible for:
 *     <ul>
 *         <li>Handling topic creation requests.</li>
 *         <li>Handling topic retrieval requests by ID.</li>
 *         <li>Handling topic listing requests.</li>
 *         <li>Handling topic update requests.</li>
 *         <li>Handling topic deletion requests.</li>
 *     </ul>
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@RestController
@RequestMapping("/api/v1/topics")
public class TopicController {

    private final CreateTopicUseCase createTopicUseCase;
    private final ListTopicsUseCase listTopicsUseCase;
    private final GetTopicDetailsUseCase getTopicDetailsUseCase;
    private final UpdateTopicUseCase updateTopicUseCase;
    private final DeleteTopicUseCase deleteTopicUseCase;

    /**
     * Constructs a new {@link TopicController} with the specified use cases.
     *
     * @param createTopicUseCase the use case for creating topics
     * @param listTopicsUseCase the use case for retrieving recent topics
     * @param getTopicDetailsUseCase the use case for retrieving a topic
     * @param updateTopicUseCase the use case for updating a topic
     * @param deleteTopicUseCase the use case for deleting a topic
     */
    public TopicController(CreateTopicUseCase createTopicUseCase,
                           ListTopicsUseCase listTopicsUseCase,
                           GetTopicDetailsUseCase getTopicDetailsUseCase,
                           UpdateTopicUseCase updateTopicUseCase,
                           DeleteTopicUseCase deleteTopicUseCase) {
        this.createTopicUseCase = createTopicUseCase;
        this.getTopicDetailsUseCase = getTopicDetailsUseCase;
        this.listTopicsUseCase = listTopicsUseCase;
        this.updateTopicUseCase = updateTopicUseCase;
        this.deleteTopicUseCase = deleteTopicUseCase;
    }

    /**
     * Endpoint for handling topic creation.
     * This method creates a topic and returns the created topic data.
     *
     * @param requestDTO {@link TopicCreateRequestDTO} the data transfer object containing the topic creation data
     * @return a {@link ResponseEntity} of {@link TopicResponseDTO} with status 201 (Created) and the created topic data
     */
    @PostMapping
    public ResponseEntity<TopicResponseDTO> createTopic(@Valid @RequestBody TopicCreateRequestDTO requestDTO) {

        TopicResponseDTO responseDTO = createTopicUseCase.execute(requestDTO);

        URI location = URI.create("/topics/" + responseDTO.id());
        return ResponseEntity.created(location).body(responseDTO);
    }

    /**
     * Retrieves a topic details by its unique identifier.
     *
     * @param id the unique identifier of the topic
     * @return the response entity containing the topic
     */
    @GetMapping("/{id}")
    public ResponseEntity<TopicDetailsResponseDTO> getTopicDetails(@Valid @PathVariable
                                                            @org.hibernate.validator.constraints.UUID String id) {
        return ResponseEntity.ok(getTopicDetailsUseCase.execute(UUID.fromString(id)));
    }

    /**
     * Endpoint for handling listing of topics with pagination support.
     * This method lists topics with pagination support and returns the list of topics.
     *
     * @param page {@link Integer} the page number to retrieve
     * @param size {@link Integer} the number of topics to retrieve per page
     * @return a {@link ResponseEntity} of {@link List} of {@link ForumResponseDTO} with status 200 (OK) and the list of topics
     */
    @GetMapping("/all")
    public ResponseEntity<List<TopicResponseDTO>> listForumsPageable(@Valid
                                                                     @RequestParam(defaultValue = "0")
                                                                     @Min(value = 0, message = "Page number must be greater than or equal to 0")
                                                                     int page,
                                                                     @Valid
                                                                     @RequestParam(defaultValue = "10")
                                                                     int size) {
        return ResponseEntity.ok(listTopicsUseCase.execute(page, size));
    }

    /**
     * Endpoint for handling topic update operations.
     * This method updates a topic by its unique identifier if the authenticated user is the topic's owner.
     *
     * @param id the topic's unique identifier of type {@link UUID} to be updated
     * @param requestDTO the data transfer object containing the topic update data
     * @return a {@link ResponseEntity} of {@link TopicResponseDTO} with status 200 (OK) and the updated topic data
     */
    @PutMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> updateTopic(@Valid @PathVariable
                                                        @org.hibernate.validator.constraints.UUID String id,
                                                        @Valid @RequestBody
                                                        TopicUpdateRequestDTO requestDTO) {
        UUID authenticatedUserId = getAuthenticatedUserId();

        return ResponseEntity.ok(updateTopicUseCase.execute(UUID.fromString(id), requestDTO, authenticatedUserId));
    }

    /**
     * Endpoint for handling forum deletion operations.
     * This method deletes a topic by its unique identifier.
     *
     * @param id the topic's unique identifier of type {@link UUID} to be deleted
     * @return a {@link ResponseEntity} with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@Valid @PathVariable @org.hibernate.validator.constraints.UUID String id) {
        UUID authenticatedUserId = getAuthenticatedUserId();
        deleteTopicUseCase.execute(UUID.fromString(id), authenticatedUserId);
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
