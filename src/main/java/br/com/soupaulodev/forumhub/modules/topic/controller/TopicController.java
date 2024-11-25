package br.com.soupaulodev.forumhub.modules.topic.controller;

import br.com.soupaulodev.forumhub.modules.topic.controller.dto.CreateTopicRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.controller.dto.UpdateTopicRequestDTO;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.mapper.TopicMapper;
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
    public ResponseEntity<TopicResponseDTO> createTopic(@Valid @RequestBody CreateTopicRequestDTO requestDTO) {
        TopicEntity topicCreated = createTopicUsecase.execute(TopicMapper.toEntity(requestDTO));
        URI location = URI.create("/topics/" + topicCreated.getId());
        return ResponseEntity.created(location).body(TopicMapper.toResponseDTO(topicCreated));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> getTopic(@PathVariable UUID id) {
        return ResponseEntity.ok(TopicMapper.toResponseDTO(getTopicUsecase.execute(id)));
    }

    @GetMapping("/recents/{page}")
    public ResponseEntity<List<TopicResponseDTO>> getRecentTopics(@PathVariable int page) {
        List<TopicResponseDTO> topics = getRecentTopicsUsecase.execute(page)
                .stream().map(TopicMapper::toResponseDTO).toList();
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/search/{query}/{page}")
    public ResponseEntity<List<TopicResponseDTO>> searchTopics(@PathVariable String query, @PathVariable int page) {
        List<TopicResponseDTO> topics = searchTopicsUsecase.execute(query, page)
                .stream().map(TopicMapper::toResponseDTO).toList();
        return ResponseEntity.ok(topics);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicResponseDTO> updateTopic(@PathVariable UUID id,
                                                        @Valid @RequestBody UpdateTopicRequestDTO requestDTO) {
        TopicEntity topicUpdated = updateTopicUsecase.execute(id, TopicMapper.toEntity(requestDTO));
        return ResponseEntity.ok(TopicMapper.toResponseDTO(topicUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable UUID id) {
        deleteTopicUsecase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
