package br.com.soupaulodev.forumhub.modules.topic.controller;

import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.usecase.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing topics.
 */
@RestController
@RequestMapping("/topics")
public class TopicController {

    private final CreateTopicUsecase createTopicUsecase;
    private final GetTopicUsecase getTopicUsecase;
    private final GetRecentTopicsUsecase getRecentTopicsUsecase;
    private final SearchTopicsUsecase searchTopicsUsecase;
    private final UpdateTopicUsecase updateTopicUsecase;
    private final DeleteTopicUsecase deleteTopicUsecase;

    /**
     * Constructs a new TopicController with the specified use cases.
     *
     * @param createTopicUsecase the use case for creating topics
     * @param getTopicUsecase the use case for retrieving a topic
     * @param getRecentTopicsUsecase the use case for retrieving recent topics
     * @param searchTopicsUsecase the use case for searching topics
     * @param updateTopicUsecase the use case for updating a topic
     * @param deleteTopicUsecase the use case for deleting a topic
     */
    public TopicController(CreateTopicUsecase createTopicUsecase,
                           GetTopicUsecase getTopicUsecase,
                           GetRecentTopicsUsecase getRecentTopicsUsecase,
                           SearchTopicsUsecase searchTopicsUsecase,
                           UpdateTopicUsecase updateTopicUsecase,
                           DeleteTopicUsecase deleteTopicUsecase) {
        this.createTopicUsecase = createTopicUsecase;
        this.getTopicUsecase = getTopicUsecase;
        this.getRecentTopicsUsecase = getRecentTopicsUsecase;
        this.searchTopicsUsecase = searchTopicsUsecase;
        this.updateTopicUsecase = updateTopicUsecase;
        this.deleteTopicUsecase = deleteTopicUsecase;
    }

    /**
     * Creates a new topic.
     *
     * @param requestDTO the data transfer object containing the topic creation data
     * @return the response entity containing the created topic
     */
    @PostMapping
    public ResponseEntity<TopicResponseDTO> createTopic(@Valid @RequestBody TopicCreateRequestDTO requestDTO) {

        TopicResponseDTO responseDTO = createTopicUsecase.execute(requestDTO);

        URI location = URI.create("/topics/" + responseDTO.getId());
        return ResponseEntity.created(location).body(responseDTO);
    }

    /**
     * Retrieves a topic by its unique identifier.
     *
     * @param id the unique identifier of the topic
     * @return the response entity containing the topic
     */
    @GetMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> getTopic(@PathVariable UUID id) {

        return ResponseEntity.ok(getTopicUsecase.execute(id));
    }

    /**
     * Retrieves recent topics.
     *
     * @param page the page number to retrieve
     * @return the response entity containing the list of recent topics
     */
    @GetMapping("/recents/{page}")
    public ResponseEntity<List<TopicResponseDTO>> getRecentTopics(@PathVariable int page) {

        return ResponseEntity.ok(getRecentTopicsUsecase.execute(page));
    }

    /**
     * Searches for topics based on a query.
     *
     * @param query the search query to match against the title or content of topics
     * @param page the page number to retrieve
     * @return the response entity containing the list of search results
     */
    @GetMapping("/search/{query}/{page}")
    public ResponseEntity<List<TopicResponseDTO>> searchTopics(@PathVariable String query, @PathVariable int page) {

        return ResponseEntity.ok(searchTopicsUsecase.execute(query, page));
    }

    /**
     * Updates an existing topic.
     *
     * @param id the unique identifier of the topic to be updated
     * @param requestDTO the data transfer object containing the topic update data
     * @return the response entity containing the updated topic
     */
    @PutMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> updateTopic(@PathVariable UUID id,
                                                        @Valid @RequestBody TopicUpdateRequestDTO requestDTO) {

        return ResponseEntity.ok(updateTopicUsecase.execute(id, requestDTO));
    }

    /**
     * Deletes a topic by its unique identifier.
     *
     * @param id the unique identifier of the topic to be deleted
     * @return the response entity indicating the deletion status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable UUID id) {

        deleteTopicUsecase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
