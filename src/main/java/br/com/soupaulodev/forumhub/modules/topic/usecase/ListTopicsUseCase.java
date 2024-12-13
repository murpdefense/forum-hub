package br.com.soupaulodev.forumhub.modules.topic.usecase;

import br.com.soupaulodev.forumhub.modules.topic.controller.dto.TopicResponseDTO;
import br.com.soupaulodev.forumhub.modules.topic.entity.TopicEntity;
import br.com.soupaulodev.forumhub.modules.topic.mapper.TopicMapper;
import br.com.soupaulodev.forumhub.modules.topic.repository.TopicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Use case for listing topics with pagination support.
 */
@Service
public class ListTopicsUseCase {

    private final TopicRepository topicRepository;

    /**
     * Constructs a new {@link ListTopicsUseCase} with the specified repository.
     *
     * @param topicRepository the repository for managing topics
     */
    public ListTopicsUseCase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * Executes the use case to list topics with pagination support.
     *
     * @param page the page number to retrieve
     * @return a {@link List} of {@link TopicResponseDTO} containing the topic data
     */
    public List<TopicResponseDTO> execute(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<TopicEntity> entities = topicRepository.findAll(pageable);

        return entities.getContent().stream()
                .map(TopicMapper::toResponseDTO)
                .toList();
    }
}
