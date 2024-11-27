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

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final CreateTopicUsecase createTopicUsecase;
    private final GetTopicUsecase getTopicUsecase;
    private final GetRecentTopicsUsecase getRecentTopicsUsecase;
    private final SearchTopicsUsecase searchTopicsUsecase;
    private final UpdateTopicUsecase updateTopicUsecase;
    private final DeleteTopicUsecase deleteTopicUsecase;

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

    @PostMapping
    public ResponseEntity<TopicResponseDTO> createTopic(@Valid @RequestBody TopicCreateRequestDTO requestDTO) {

        TopicResponseDTO responseDTO = createTopicUsecase.execute(requestDTO);

        URI location = URI.create("/topics/" + responseDTO.getId());
        return ResponseEntity.created(location).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> getTopic(@PathVariable UUID id) {

        return ResponseEntity.ok(getTopicUsecase.execute(id));
    }

    @GetMapping("/recents/{page}")
    public ResponseEntity<List<TopicResponseDTO>> getRecentTopics(@PathVariable int page) {

        return ResponseEntity.ok(getRecentTopicsUsecase.execute(page));
    }

    @GetMapping("/search/{query}/{page}")
    public ResponseEntity<List<TopicResponseDTO>> searchTopics(@PathVariable String query, @PathVariable int page) {

        return ResponseEntity.ok(searchTopicsUsecase.execute(query, page));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> updateTopic(@PathVariable UUID id,
                                                        @Valid @RequestBody TopicUpdateRequestDTO requestDTO) {

        return ResponseEntity.ok(updateTopicUsecase.execute(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable UUID id) {

        deleteTopicUsecase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
